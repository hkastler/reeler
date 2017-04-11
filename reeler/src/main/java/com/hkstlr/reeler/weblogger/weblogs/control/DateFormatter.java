/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public class DateFormatter {

    public DateFormatter() {
    }
    
    /**
     * yyyy MMM dd HH:mm
     */
    public static DateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");
    
    /**
     * yyyy
     */
    public static DateFormat yearFormat = new SimpleDateFormat("yyyy");
    
    /**
     * MM
     */
    public static DateFormat monthFormat = new SimpleDateFormat("MM");
    public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static DateFormat dayFormat = new SimpleDateFormat("d");
    public static DateFormat format8chars = new SimpleDateFormat("YYYYMMDD");
    public static DateFormat yearMonthFormat = new SimpleDateFormat("yyyyMMM");
    
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * thanks to https://github.com/jarrodhroberson/Stack-Overflow/blob/master/src/main/java/com/stackoverflow/Q2597083.java
     */
    public static DateFormat jsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
    
    static public DateFormat localeDefaultDateFormat(Locale currentLocale) {
        DateFormat localeDateFormat = DateFormat.getDateInstance(DateFormat.SHORT ,currentLocale);
        
        return localeDateFormat;
    }

}
