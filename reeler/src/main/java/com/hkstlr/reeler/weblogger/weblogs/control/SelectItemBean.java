/*
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
package com.hkstlr.reeler.weblogger.weblogs.control;


import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;


/**
 *
 * @author henry.kastler
 */
@Named(value = "selectItemBean")
@ApplicationScoped
public class SelectItemBean {

    
    private SelectItem[] monthItems;    
    private SelectItem[] yearItems;
    private SelectItem[] weblogCategoryItems;
    private SelectItem[] hourItems;
    private SelectItem[] minuteItems;
    private SelectItem[] secondItems;
    private SelectItem[] commentDaysItems;
    private SelectItem[] localeItems;

    @Inject
    private Logger log;
  
    /**
     * Creates a new instance of SelectItemBean
     */
    public SelectItemBean() {
        //constructor
    }
    
    @PostConstruct
    protected void init(){
        monthItems = getMonthSelectItems();
        yearItems = getYearSelectItems();
        hourItems = getHourSelectItems();
        minuteItems = getMinuteSelectItems();
        secondItems = getSecondSelectItems();
        commentDaysItems = getCommentDaysSelectItems();
        localeItems = getLocaleItems();
    }
    
    
    public SelectItem[] getCommentDaysItems() {
        return commentDaysItems;
    }

    public void setCommentDaysItems(SelectItem[] commentDaysItems) {
        this.commentDaysItems = commentDaysItems;
    }
    
    public SelectItem[] getMonthItems() {
        return monthItems;
    }

    public void setMonthItems(SelectItem[] monthItems) {
        this.monthItems = monthItems;
    }

    public SelectItem[] getYearItems() {
        return yearItems;
    }

    public void setYearItems(SelectItem[] yearItems) {
        this.yearItems = yearItems;
    }

    public SelectItem[] getWeblogCategoryItems() {
        return weblogCategoryItems;
    }

    public void setWeblogCategoryItems(SelectItem[] weblogCategoryItems) {
        this.weblogCategoryItems = weblogCategoryItems;
    }

    public SelectItem[] getHourItems() {
        return hourItems;
    }

    public void setHourItems(SelectItem[] hourItems) {
        this.hourItems = hourItems;
    }

    public SelectItem[] getMinuteItems() {
        return minuteItems;
    }

    public void setMinuteItems(SelectItem[] minuteItems) {
        this.minuteItems = minuteItems;
    }

    public SelectItem[] getSecondItems() {
        return secondItems;
    }

    public void setSecondItems(SelectItem[] secondItems) {
        this.secondItems = secondItems;
    }
    
    

    private SelectItem[] getMonthSelectItems() {
        SelectItem[] items = new SelectItem[12];
        
        for(int i=0; i<=11; i++){
            
            String strMonth = Integer.toString(i+1);
            if(strMonth.length()==1){
                strMonth= "0"+strMonth;
            }
            items[i] = new SelectItem(i+1,strMonth);
        }
        
        return items;
    }
    
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
    
    private SelectItem[] getYearSelectItems() {
        int currentYear;
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        int yearsToAppear = 10;
        SelectItem[] items = new SelectItem[yearsToAppear];
        int counter = 0;
        for(int i=currentYear; i<currentYear+yearsToAppear; i++){
            items[counter] = new SelectItem(i,Integer.toString(i));
            counter++;
        }
        
        return items;
    }
    
    
    private SelectItem[] getHourSelectItems() {
              
        SelectItem[] items = new SelectItem[24];
       
        for(int i=0; i<24; i++){
            items[i] = new SelectItem(i,Integer.toString(i));
           
        }
        
        return items;
    }
    
    private SelectItem[] getMinuteSelectItems() {
              
        SelectItem[] items = new SelectItem[60];
        int counter = 0;
        for(int i=0; i<60; i++){
            items[counter] = new SelectItem(i,Integer.toString(i));
            counter++;
        }
        
        return items;
    }
    
    private SelectItem[] getSecondSelectItems() {
        SelectItem[] items = new SelectItem[60];
        int counter = 0;
        for(int i=0; i<60; i++){
            items[counter] = new SelectItem(i,Integer.toString(i));
            counter++;
        }
        
        return items;
    }
    
    private SelectItem[] getCommentDaysSelectItems() {
        String dayLabel = "days";
       
        SelectItem[] items = new SelectItem[7];
        SelectItem item = new SelectItem();
        item.setValue(0);
        item.setLabel("unlimited days");
        items[0] = item;
        
        item = new SelectItem();
        item.setValue(3);
        item.setLabel("3 ".concat(dayLabel));
        items[1] = item;
        
        item = new SelectItem();
        item.setValue(7);
        item.setLabel("7 ".concat(dayLabel));
        items[2] = item;
        
        item = new SelectItem();
        item.setValue(14);
        item.setLabel("14 ".concat(dayLabel));
        items[3] = item;
        
        item = new SelectItem();
        item.setValue(30);
        item.setLabel("30 ".concat(dayLabel));
        items[4] = item;
        
        item = new SelectItem();
        item.setValue(60);
        item.setLabel("60 ".concat(dayLabel));
        items[5] = item;
        
        item = new SelectItem();
        item.setValue(90);
        item.setLabel("90 ".concat(dayLabel));
        items[6] = item;
        
        return items;
    }
    
    
    
    public SelectItem[] getLocaleItems() {
       
       Locale[] locales = Calendar.getAvailableLocales();       
       List<SelectItem> ftLocale = new ArrayList<>();        
       for (Locale locale : locales) { 
            if(locale.getDisplayCountry().length() > 0){
                ftLocale.add(new SelectItem(locale.toString(),locale.getDisplayCountry()) );
            }
       }              
               
       
        Comparator<SelectItem> localeNameSort = (SelectItem si1, SelectItem si2) 
                -> si1.getLabel().substring(0,1).compareTo(si2.getLabel().substring(0,1));
        
        Collections.sort(ftLocale,localeNameSort);
       
        return ftLocale.toArray(new SelectItem[ftLocale.size()]);
    }
    
     public SelectItem[] getTimeZoneItems() {
       
       String[] timeZones = TimeZone.getAvailableIDs();
       List<SelectItem> ftZones = new ArrayList<>();        
       for (String tz : timeZones) { 
           ftZones.add(new SelectItem(tz,tz) );
       }              
        
       Comparator<SelectItem> timeZoneNameSort = (SelectItem si1, SelectItem si2) 
                -> si1.getLabel().substring(0,1).compareTo(si2.getLabel().substring(0,1));
        
        Collections.sort(ftZones,timeZoneNameSort);
       
        return ftZones.toArray(new SelectItem[ftZones.size()]);
    }
     
     
}
