/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public class DateFormatter {

    private DateFormatter() {
        //constructor
    }
    
    /**
     * yyyy MMM dd HH:mm
     */
    public static final DateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");
    
    /**
     * yyyy
     */
    public static final DateFormat yearFormat = new SimpleDateFormat("yyyy");
    
    /**
     * MM
     */
    public static final DateFormat monthFormat = new SimpleDateFormat("MM");
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat dayFormat = new SimpleDateFormat("d");
    public static final DateFormat format8chars = new SimpleDateFormat("YYYYMMDD");
    public static final DateFormat yearMonthFormat = new SimpleDateFormat("yyyyMMM");
    
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * thanks to https://github.com/jarrodhroberson/Stack-Overflow/blob/master/src/main/java/com/stackoverflow/Q2597083.java
     */
    public static final DateFormat jsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
    
    static public DateFormat localeDefaultDateFormat(Locale currentLocale) {
        DateFormat localeDateFormat = DateFormat.getDateInstance(DateFormat.SHORT ,currentLocale);
        
        return localeDateFormat;
    }

}
