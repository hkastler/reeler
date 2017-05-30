//org from org.apache.roller.util.Utilities
//replaced apache lib imports
package com.hkstlr.reeler.weblogger.plugins.control;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringFixer {

    private static final Logger LOG = Logger.getLogger(StringFixer.class.getName());  
    

    public static final String TAG_SPLIT_CHARS = " ,\n\r\f\t";
    private static final Pattern OPENING_B_TAG_PATTERN = Pattern.compile(
            "&lt;b&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_B_TAG_PATTERN = Pattern.compile(
            "&lt;/b&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_I_TAG_PATTERN = Pattern.compile(
            "&lt;i&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_I_TAG_PATTERN = Pattern.compile(
            "&lt;/i&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_BLOCKQUOTE_TAG_PATTERN = Pattern
            .compile("&lt;blockquote&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_BLOCKQUOTE_TAG_PATTERN = Pattern
            .compile("&lt;/blockquote&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern BR_TAG_PATTERN = Pattern.compile(
            "&lt;br */*&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_P_TAG_PATTERN = Pattern.compile(
            "&lt;p&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_P_TAG_PATTERN = Pattern.compile(
            "&lt;/p&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_PRE_TAG_PATTERN = Pattern.compile(
            "&lt;pre&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_PRE_TAG_PATTERN = Pattern.compile(
            "&lt;/pre&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_UL_TAG_PATTERN = Pattern.compile(
            "&lt;ul&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_UL_TAG_PATTERN = Pattern.compile(
            "&lt;/ul&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_OL_TAG_PATTERN = Pattern.compile(
            "&lt;ol&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_OL_TAG_PATTERN = Pattern.compile(
            "&lt;/ol&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_LI_TAG_PATTERN = Pattern.compile(
            "&lt;li&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_LI_TAG_PATTERN = Pattern.compile(
            "&lt;/li&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSING_A_TAG_PATTERN = Pattern.compile(
            "&lt;/a&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern OPENING_A_TAG_PATTERN = Pattern.compile(
            "&lt;a href=.*?&gt;", Pattern.CASE_INSENSITIVE);
    private static final Pattern QUOTE_PATTERN = Pattern.compile("&quot;",
            Pattern.CASE_INSENSITIVE);

    //from RegexUtil in roller
    public static final Pattern MAILTO_PATTERN
            = Pattern.compile("mailto:([a-zA-Z0-9\\.\\-]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z0-9]+)");

    public static final Pattern EMAIL_PATTERN
            = Pattern.compile("\\b[a-zA-Z0-9\\.\\-]+(@)([a-zA-Z0-9\\.\\-]+)(\\.)([a-zA-Z0-9]+)\\b");

    /**
     * Transforms the given String into a subset of HTML displayable on a web
     * page. The subset includes &lt;b&gt;, &lt;i&gt;, &lt;p&gt;, &lt;br&gt;,
     * &lt;pre&gt; and &lt;a href&gt; (and their corresponding end tags).
     *
     * @param s the String to transform
     * @return the transformed String
     */
    public static String transformToHTMLSubset(String str) {

        if (str == null) {
            return null;
        }

        String s = str;

        s = patternReplace(s, OPENING_B_TAG_PATTERN, "<b>");
        s = patternReplace(s, CLOSING_B_TAG_PATTERN, "</b>");
        s = patternReplace(s, OPENING_I_TAG_PATTERN, "<i>");
        s = patternReplace(s, CLOSING_I_TAG_PATTERN, "</i>");
        s = patternReplace(s, OPENING_BLOCKQUOTE_TAG_PATTERN, "<blockquote>");
        s = patternReplace(s, CLOSING_BLOCKQUOTE_TAG_PATTERN, "</blockquote>");
        s = patternReplace(s, BR_TAG_PATTERN, "<br />");
        s = patternReplace(s, OPENING_P_TAG_PATTERN, "<p>");
        s = patternReplace(s, CLOSING_P_TAG_PATTERN, "</p>");
        s = patternReplace(s, OPENING_PRE_TAG_PATTERN, "<pre>");
        s = patternReplace(s, CLOSING_PRE_TAG_PATTERN, "</pre>");
        s = patternReplace(s, OPENING_UL_TAG_PATTERN, "<ul>");
        s = patternReplace(s, CLOSING_UL_TAG_PATTERN, "</ul>");
        s = patternReplace(s, OPENING_OL_TAG_PATTERN, "<ol>");
        s = patternReplace(s, CLOSING_OL_TAG_PATTERN, "</ol>");
        s = patternReplace(s, OPENING_LI_TAG_PATTERN, "<li>");
        s = patternReplace(s, CLOSING_LI_TAG_PATTERN, "</li>");
        s = patternReplace(s, QUOTE_PATTERN, "\"");

        // HTTP links
        s = patternReplace(s, CLOSING_A_TAG_PATTERN, "</a>");
        Matcher m = OPENING_A_TAG_PATTERN.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String link = s.substring(start, end);
            link = "<" + link.substring(4, link.length() - 4) + ">";
            s = s.substring(0, start) + link + s.substring(end, s.length());
            m = OPENING_A_TAG_PATTERN.matcher(s);
        }

        // escaped angle brackets
        s = s.replaceAll("&amp;lt;", "&lt;");
        s = s.replaceAll("&amp;gt;", "&gt;");
        s = s.replaceAll("&amp;#", "&#");

        return s;
    }

    private static String patternReplace(String string, Pattern pattern,
            String replacement) {

        Matcher m = pattern.matcher(string);

        return m.replaceAll(replacement);
    }

    public static String encodeEmail(String str) {
        // obfuscate mailto's: turns them into hex encoded,
        // so that browsers can still understand the mailto link
        String lstr = str;
        Matcher mailtoMatch = MAILTO_PATTERN.matcher(str);
        while (mailtoMatch.find()) {
            String email = mailtoMatch.group(1);
            String hexed = encode(email);
            lstr = lstr.replaceFirst("mailto:" + email, "mailto:" + hexed);
        }

        return obfuscateEmail(lstr);
    }

    /**
     * obfuscate plaintext emails: makes them "human-readable" - still too easy
     * for machines to parse however.
     *
     * @param str
     * @return
     */
    public static String obfuscateEmail(String str) {
        String lstr = str;
        Matcher emailMatch = EMAIL_PATTERN.matcher(str);
        while (emailMatch.find()) {
            String at = emailMatch.group(1);            
            lstr = lstr.replaceFirst(at, "-AT-");
            String dot = emailMatch.group(2) + emailMatch.group(3) + emailMatch.group(4);
            String newDot = emailMatch.group(2) + "-DOT-" + emailMatch.group(4);            
            lstr = lstr.replaceFirst(dot, newDot);
        }
        return lstr;
    }

    /**
     * Return the specified match "groups" from the pattern. For each group
     * matched a String will be entered in the ArrayList.
     *
     * @param pattern The Pattern to use.
     * @param match The String to match against.
     * @param group The group number to return in case of a match.
     * @return List of matched groups from the pattern.
     */
    public static List<String> getMatches(Pattern pattern, String match, int group) {
        List<String> matches = new ArrayList<>();
        Matcher matcher = pattern.matcher(match);
        while (matcher.find()) {
            matches.add(matcher.group(group));
        }
        return matches;
    }

    /**
     * Thanks to the folks at Blojsom (http://sf.net/projects/blojsom) for
     * showing me what I was doing wrong with the Hex class.
     *
     * @param email
     * @return
     */
    public static String encode(String email) {
        
        try {
            return encodeHex(email.getBytes("UTF-8"));
            
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.FINE,"emailEncodeIssue",e);
            return email;
        }

       
    }

    public static String encodeHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
    
    public static String newLineToHtml(String str){
        
        
        /* setup a buffered reader and iterate through each line
         * inserting html as needed
         *
         * NOTE: we consider a paragraph to be 2 endlines with no text between them
         */
        StringBuilder buf = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new StringReader(str));

            String line;
            boolean insidePara = false;
            while ((line = br.readLine()) != null) {

                if (!insidePara && line.trim().length() > 0) {
                    // start of a new paragraph
                    buf.append("\n<p>");
                    buf.append(line);
                    insidePara = true;
                } else if (insidePara && line.trim().length() > 0) {
                    // another line in an existing paragraph
                    buf.append("<br/>\n");
                    buf.append(line);
                } else if (insidePara && line.trim().length() < 1) {
                    // end of a paragraph
                    buf.append("</p>\n\n");
                    insidePara = false;
                }
            }

            // if the text ends without an empty line then we need to
            // terminate the last paragraph now
            if (insidePara) {
                buf.append("</p>\n\n");
            }

        } catch (Exception e) {
            LOG.log(Level.WARNING,"trouble rendering text." + e.getMessage(),e);
            return str;
        }

        return buf.toString();
    }

}
