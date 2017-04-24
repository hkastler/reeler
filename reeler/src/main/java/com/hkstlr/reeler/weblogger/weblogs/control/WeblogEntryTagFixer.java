/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryTagFixer {
    
    public static final String TAG_SPLIT_REGEX = "/[ ,]+/";
    /**
     * @param tag
     * @return
     */
    public static String stripInvalidTagCharacters(String tag) {
        if (tag == null) {
            throw new NullPointerException();
        }

        StringBuilder sb = new StringBuilder();
        char[] charArray = tag.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            // fast-path exclusions quotes and commas are obvious
            // 34 = double-quote, 44 = comma
            switch (c) {
            case 34:
            case 44:
                continue;
            }

            if ((33 <= c && c <= 126) || Character.isUnicodeIdentifierPart(c)
                    || Character.isUnicodeIdentifierStart(c)) {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    public static String normalizeTag(String vtag, Locale locale) {
        String tag = vtag;
        tag = stripInvalidTagCharacters(tag);
        return locale == null ? tag.toLowerCase() : tag.toLowerCase(locale);
    }

    /**
     * @param tags String holding space separated list of tags
     * @return List of strings, one string for each tag
     */
    public static List<String> splitStringAsTags(String tags) {
        String[] tagsarr = tags.split(" *(,|=>| ) *");
        if (tagsarr == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(tagsarr);
    }
}
