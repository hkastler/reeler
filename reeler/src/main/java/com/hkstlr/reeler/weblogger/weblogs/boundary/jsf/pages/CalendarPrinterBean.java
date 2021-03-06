/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
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
import javax.ejb.EJB;
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
    private CalendarPrinter calendarPrinter;
    
    @EJB
    private Weblogger weblogger;
    
    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.anchor}")
    private String anchor;
    
    @ManagedProperty(value = "#{param.dateString}")
    private String dateString = StringPool.BLANK;
    
    List<Calendar> weblogDates = new ArrayList();
    
    public CalendarPrinterBean() {
        //default constructor
    }
    
    @PostConstruct
    public void init(){
        if(dateString == null){
            dateString = DateFormatter.dateFormat.format(new Date());
        }
        if(handle == null){
            handle = StringPool.BLANK;
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
        return weblogger.getWeblogEntryManager().getWeblogEntryDatesForCalendar(dateString, weblog);
    }
    
    public String calendarTable(String path, Weblog weblog) throws ParseException {        
        
        Date incomingDate = new Date();
        if(dateString.length() == 8){
          
           incomingDate = DateFormatter.dateFormat.parse(dateString);
          
        }else if(dateString.length() == 6){
           dateString += "01";
           incomingDate = DateFormatter.dateFormat.parse(dateString);
           
        }
        
        weblogDates = loadWeblogDates(dateString, weblog);
        
        return calendarPrinter.calendarTable(weblogDates, incomingDate, path, weblog.getHandle());
    }
    
}
