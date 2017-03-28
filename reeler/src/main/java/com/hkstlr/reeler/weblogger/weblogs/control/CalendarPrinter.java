/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.text.DateFormatSymbols;
import static java.text.MessageFormat.format;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@RequestScoped
@Named
public class CalendarPrinter {

    @Inject
    private Logger log;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    //Date now = new Date();
    Calendar calendar;
    
    List<String> calendarDates = new ArrayList();
    
    @Inject
    WeblogEntryManager wem;
    
    Date now = new Date();
    
    int year;
    int month; // Jan = 0, dec = 11
    int dayOfMonth;
    int dayOfWeek;
    //int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
    //int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

    /* int hour = calendar.get(Calendar.HOUR);        // 12 hour clock
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    int millisecond = calendar.get(Calendar.MILLISECOND);*/
    
    DateFormatSymbols symbols = new DateFormatSymbols();
   
    String[] dayNames = symbols.getShortWeekdays(); 

    public CalendarPrinter() {
        //calendar = new GregorianCalendar(2017, Calendar.MARCH, 1);
    }
    
    public CalendarPrinter(int year, int month, int day) {
        //calendar = new GregorianCalendar(year, month, day);
    }
    
    @PostConstruct
    private void init(){
        //List<Object> dates = wem.getWeblogEntryDatesForCalendar("201703", weblog);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        
        String strYear = yearFormat.format(now);
        String strMonth = monthFormat.format(now);
        
        calendar = new GregorianCalendar(Integer.parseInt(strYear),Integer.parseInt(strMonth)-1,1);
        //calendar.setTime(now);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
    }
    
    public void calendarSetup(){
        
    }

    public String[] getDayNames() {
        return dayNames;
    }

    public void setDayNames(String[] dayNames) {
        this.dayNames = dayNames;
    }
    
    private String dateHrefTemplate(Object[] args){
        String template = "<a href=\"{0}/{1}/date/{2}\">";
        String formatted = format(template,args);
        return formatted;
    }
    
    public String calendarTable(String path, Weblog weblog){        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(new Date());
        
        return calendarTable(formattedDate, path, weblog);
    }
    
    
    public String calendarTable(String dateString,String path, Weblog weblog){
        
        SimpleDateFormat sdf = new SimpleDateFormat("d");

        List<Calendar> dates = wem.getWeblogEntryDatesForCalendar(dateString, weblog);        
        log.fine("dates:" + dates.toString());
        List<Integer> days = new ArrayList<>();
        
        for(Calendar calDate : dates){
            Date theDate = calDate.getTime();
            String day = sdf.format(theDate);
            log.fine("day:" + day);
            days.add(Integer.parseInt(day));
        }
        
        
        StringBuilder calTable = new StringBuilder("<table class=\"table fixed-table-body table-striped\">");
        calTable.append("<thead class=\"thead-default\">");
            calTable.append("<tr>");
                calTable.append("<td colspan=\"1\" align=\"left\">");
                    //calTable.append("<a href=\"date\">");
                   // calTable.append("<i class=\"fa fa-arrow-left\" aria-hidden=\"true\"></i>");
                   // calTable.append("</a>");
                calTable.append("</td>");
                calTable.append("<td colspan=\"5\" align=\"center\">");
                    calTable.append(Month.of(month+1));
                    calTable.append(" ");
                    calTable.append(year);
                calTable.append("</td>");
                calTable.append("<td colspan=\"1\" align=\"right\">");
                    //calTable.append("<a href=\"date\">");
                    //calTable.append("<i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>");
                    //calTable.append("</a>");
                calTable.append("</td>");
            calTable.append("</tr>");
       
        calTable.append("<tr>");
        for(String dayName : dayNames){
            if(dayName.length()>0){
                calTable.append("<th>");
                calTable.append(dayName);
                calTable.append("</th>");
            }
        }
        calTable.append("</tr>");
        calTable.append("</thead>");
        
        calTable.append("<tbody>");
        calTable.append("<tr>");
        if(calendar.getFirstDayOfWeek() == Calendar.MONDAY){
            if(dayOfWeek == 1){
                dayOfWeek +=6;
            }else{
                dayOfWeek--;
            }
        }
        for(int i = 1; i < dayOfWeek; i++){
            calTable.append("<td>");
        }
        dayOfWeek = 0;
        for(int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            if(dayOfWeek > 7){
                calTable.append("</tr><tr>");
            }           
            Calendar tempCal = (Calendar) calendar.clone();
            tempCal.set(Calendar.MONTH, month);
            tempCal.set(Calendar.DATE, i);
            tempCal.set(Calendar.YEAR, year);
            dayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
            dayOfMonth = tempCal.get(Calendar.DAY_OF_MONTH);
            calTable.append("<td>");
            if(days.contains(dayOfMonth)){
                //add a padding zero to the month string
                String monthString = String.format("%02d",(month+1));
                String thisDateString = Integer.toString(year)
                        .concat(monthString)
                        .concat(Integer.toString(dayOfMonth));
                calTable.append("<b>");
                calTable.append(dateHrefTemplate(new Object[]{path,weblog.getHandle(),thisDateString}));
            }
            calTable.append(Integer.toString(dayOfMonth));
            if(days.contains(dayOfMonth)){
                calTable.append("</a>");
                calTable.append("</b>");
            }
            calTable.append("</td>");
            
            dayOfWeek++;
        }
        calTable.append("</tr>");
        calTable.append("</table>");
        
        return calTable.toString();
    }
    

    
}
