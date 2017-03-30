/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.text.SimpleDateFormat;

/**
 *
 * @author henry.kastler
 */
public class DateFormatter {

    public DateFormatter() {
    }
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat dayFormat = new SimpleDateFormat("d");
}
