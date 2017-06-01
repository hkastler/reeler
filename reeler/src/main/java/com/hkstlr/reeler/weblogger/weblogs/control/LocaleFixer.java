
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public final class LocaleFixer {

    public LocaleFixer() {
        //constructor
    }
    
    public static Locale toLocale(String locale) {
        if (locale != null) {
            String parts[] = locale.split("_");
            if (parts.length == 1) {
                return new Locale(parts[0]);
            } else if (parts.length == 2
                    || (parts.length == 3 && parts[2].startsWith("#"))) {
                return new Locale(parts[0], parts[1]);
            } else {
                return new Locale(parts[0], parts[1], parts[2]);
            }
        }
        return Locale.getDefault();
    }    
}