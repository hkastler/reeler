package com.hkstlr.reeler.weblogger.control;

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

}
