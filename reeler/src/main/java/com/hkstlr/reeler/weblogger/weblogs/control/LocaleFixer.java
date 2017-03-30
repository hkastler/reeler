/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public final class LocaleFixer {
      public static Locale toLocale(String locale) {
        if (locale != null) {
            String[] localeStr = locale.split("_");
            if (localeStr.length == 1) {
                if (localeStr[0] == null) {
                    localeStr[0] = "";
                }
                return new Locale(localeStr[0]);
            } else if (localeStr.length == 2) {
                if (localeStr[0] == null) {
                    localeStr[0] = "";
                }
                if (localeStr[1] == null) {
                    localeStr[1] = "";
                }
                return new Locale(localeStr[0], localeStr[1]);
            } else if (localeStr.length == 3) {
                if (localeStr[0] == null) {
                    localeStr[0] = "";
                }
                if (localeStr[1] == null) {
                    localeStr[1] = "";
                }
                if (localeStr[2] == null) {
                    localeStr[2] = "";
                }
                return new Locale(localeStr[0], localeStr[1], localeStr[2]);
            }
        }
        return Locale.getDefault();
    }
}
