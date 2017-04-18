/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.weblogs.control.CalendarPrinter;
import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
public class CalendarPrinterBean {
    
    Logger log = Logger.getLogger(CalendarPrinterBean.class.getName());
    
    @Inject
    CalendarPrinter calendarPrinter;
    
    @Inject
    WeblogEntryManager wem;
    
    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.anchor}")
    private String anchor;
    
    @ManagedProperty(value = "#{param.dateString}")
    private String dateString = new String();
    
    List<Calendar> weblogDates = new ArrayList();

    public CalendarPrinterBean() {
    }
    
    @PostConstruct
    public void init(){
        if(dateString == null){
            dateString = DateFormatter.dateFormat.format(new Date());
        }
        if(handle == null){
            handle = new String();
        }
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
    
    public List<Calendar> getWeblogDates() {
        return weblogDates;
    }

    public void setWeblogDates(List<Calendar> weblogDates) {
        this.weblogDates = weblogDates;
    }
    
    public List<Calendar> loadWeblogDates(String dateString, Weblog weblog){
        return wem.getWeblogEntryDatesForCalendar(dateString, weblog);
    }
    
    public String calendarTable(String path, Weblog weblog) throws ParseException {        
        
        Date incomingDate = new Date();
        if(dateString.length() == 8){
          
           incomingDate = DateFormatter.dateFormat.parse(dateString);
          
        }else if(dateString.length() == 6){
           //log.info("dateString.six:" + dateString);
           dateString += "01";
           incomingDate = DateFormatter.dateFormat.parse(dateString);
           
        }
        
        weblogDates = loadWeblogDates(dateString, weblog);
        
        return calendarPrinter.calendarTable(weblogDates, incomingDate, path, handle);
    }
    
}
