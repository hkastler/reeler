/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;


import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
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

    private SelectItem[] countryItems;
    private SelectItem[] monthItems;    
    private SelectItem[] yearItems;
    private SelectItem[] weblogCategoryItems;
    private SelectItem[] hourItems;
    private SelectItem[] minuteItems;
    private SelectItem[] secondItems;
    private SelectItem[] commentDaysItems;
    private SelectItem[] localeItems;

    @Inject
    private transient Logger log;
  
    /**
     * Creates a new instance of USBean
     */
    public SelectItemBean() {
    }
    
    @PostConstruct
    private void init(){
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
            String month = getMonth(i+1);
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
    
    private SelectItem[] getWeblogCategoryItems(List<WeblogCategory> cats) {
        SelectItem[] items = new SelectItem[cats.size()];
        
        for(int i=0; i<=cats.size(); i++){
            String month = getMonth(i+1);
            String strMonth = Integer.toString(i+1);
            if(strMonth.length()==1){
                strMonth= "0"+strMonth;
            }
            items[i] = new SelectItem(i+1,strMonth);
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
        //Integer[] hours = new Integer[24];
       
       
        SelectItem[] items = new SelectItem[60];
        int counter = 0;
        for(int i=0; i<60; i++){
            items[counter] = new SelectItem(i,Integer.toString(i));
            counter++;
        }
        
        return items;
    }
    
    private SelectItem[] getSecondSelectItems() {
        //Integer[] hours = new Integer[24];
       
       
        SelectItem[] items = new SelectItem[60];
        int counter = 0;
        for(int i=0; i<60; i++){
            items[counter] = new SelectItem(i,Integer.toString(i));
            counter++;
        }
        
        return items;
    }
    
    private SelectItem[] getCommentDaysSelectItems() {
        //Integer[] hours = new Integer[24];
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
       List<SelectItem> ftLocale = new ArrayList<SelectItem>();        
       for (Locale locale : locales) { 
            if(locale.getDisplayCountry().length() > 0){
                ftLocale.add(new SelectItem(locale.toString(),locale.getDisplayCountry()) );
            }
       }              
               
       
        Comparator<SelectItem> localeNameSort = new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem si1, SelectItem si2) {
            return  si1.getLabel().substring(0,1).compareTo(si2.getLabel().substring(0,1));
        }
        };
        
        Collections.sort(ftLocale,localeNameSort);
       
        SelectItem[] cItems = ftLocale.toArray(new SelectItem[ftLocale.size()]);
        return cItems;
    }
    
     public SelectItem[] getTimeZoneItems() {
       
       String[] timeZones = TimeZone.getAvailableIDs();
       List<SelectItem> ftZones = new ArrayList<SelectItem>();        
       for (String tz : timeZones) { 
           ftZones.add(new SelectItem(tz,tz) );
       }              
              
       
        Comparator<SelectItem> timeZoneNameSort = new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem si1, SelectItem si2) {
            return  si1.getLabel().substring(0,1).compareTo(si2.getLabel().substring(0,1));
        }
        };
        
        Collections.sort(ftZones,timeZoneNameSort);
       
        SelectItem[] cItems = ftZones.toArray(new SelectItem[ftZones.size()]);
        return cItems;
    }
}
