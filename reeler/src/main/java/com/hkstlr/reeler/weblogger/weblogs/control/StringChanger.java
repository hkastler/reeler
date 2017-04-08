/*
adapted from package org.apache.roller.weblogger.util.Utilities;
*/

package com.hkstlr.reeler.weblogger.weblogs.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringChanger {

    public static String toTitleCase(String input) {
        input = input.toLowerCase();
        char c = input.charAt(0);
        String s = new String("" + c);
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    // --------------------------------------------------------------------------
    /**
     * Convert string with delimiters to string list.
     */
    public static List<String> stringToStringList(String instr, String delim) {
        List<String> stringList = new ArrayList<String>();
        String[] str = instr.split(delim);
        Collections.addAll(stringList, str);
        return stringList;
    }

    // ------------------------------------------------------------------------
    /**
     * Convert string array to string with delimeters.
     */
    public static String stringListToString(List<String> stringList,
            String delim) {
        StringBuilder bldr = new StringBuilder();
        for (String s : stringList) {
            if (bldr.length() > 0) {
                bldr.append(delim).append(s);
            } else {
                bldr.append(s);
            }
        }
        return bldr.toString();
    }
    
     // ------------------------------------------------------------------------
    /**
     * Replaces occurrences of non-alphanumeric characters with an underscore.
     */
    public static String replaceNonAlphanumeric(String str) {
        return replaceNonAlphanumeric(str, '_');
    }

    // ------------------------------------------------------------------------
    /**
     * Replaces occurrences of non-alphanumeric characters with a supplied char.
     */
    public static String replaceNonAlphanumeric(String str, char subst) {
        StringBuilder ret = new StringBuilder(str.length());
        char[] testChars = str.toCharArray();
        for (int i = 0; i < testChars.length; i++) {
            if (Character.isLetterOrDigit(testChars[i])) {
                ret.append(testChars[i]);
            } else {
                ret.append(subst);
            }
        }
        return ret.toString();
    }

    // ------------------------------------------------------------------------
    /**
     * Remove occurrences of non-alphanumeric characters.
     */
    public static String removeNonAlphanumeric(String str) {
        StringBuilder ret = new StringBuilder(str.length());
        char[] testChars = str.toCharArray();
        for (int i = 0; i < testChars.length; i++) {
            // MR: Allow periods in page links
            if (Character.isLetterOrDigit(testChars[i]) || testChars[i] == '.') {
                ret.append(testChars[i]);
            }
        }
        return ret.toString();
    }

    // ------------------------------------------------------------------------
    /** Convert string array to string with delimeters. */
    public static String stringArrayToString(String[] stringArray, String delim) {
        StringBuilder bldr = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            if (bldr.length() > 0) {
                bldr.append(delim).append(stringArray[i]);
            } else {
                bldr.append(stringArray[i]);
            }
        }
        return bldr.toString();
    }

    // --------------------------------------------------------------------------
    /** Convert string to integer array. */
    public static int[] stringToIntArray(String instr, String delim) {
        String[] str = instr.split(delim);
        int intArray[] = new int[str.length];
        int i = 0;
        for (String string : str) {
            int nInt = Integer.parseInt(string);
            intArray[i++] = nInt;
        }
        return intArray;
    }

}
