/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import java.text.DateFormatSymbols;
import static java.text.MessageFormat.format;
import java.text.ParseException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author henry.kastler
 */
public class CalendarPrinter {

   
    private Logger log = Logger.getLogger(CalendarPrinter.class.getName());
    
    //Date now = new Date();
    private Calendar calendar;

    Date now = new Date();

    private int year;
    private int month; // Jan = 0, dec = 11
    private int dayOfMonth;
    private int dayOfWeek;
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
    public void init() {
        //List<Object> dates = wem.getWeblogEntryDatesForCalendar("201703", weblog);

        String strYear = DateFormatter.yearFormat.format(now);
        String strMonth = DateFormatter.monthFormat.format(now);

        calendar = new GregorianCalendar(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //calendar.setTime(now);
        //year = ;
        //month = ; // Jan = 0, dec = 11
        //dayOfMonth = 
       // 
        
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public int getDayOfMonth() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    
    
    public String[] getDayNames() {
        return dayNames;
    }

    public void setDayNames(String[] dayNames) {
        this.dayNames = dayNames;
    }

    private String dateHrefTemplate(Object[] args) {
        String template = "<a href=\"{0}/{1}/date/{2}\">";
        String formatted = format(template, args);
        return formatted;
    }

    public String calendarTable(List<Calendar> dates, Date incomingDate, String path, String handle) throws ParseException {
              
        String incomingMonth = DateFormatter.monthFormat.format(incomingDate);        
        Integer intMonth = Integer.parseInt(incomingMonth);        
        Integer previousMonth = intMonth-1;
        Integer nextMonth = intMonth+1;
        
        String incomingYear = DateFormatter.yearFormat.format(incomingDate);
                
        calendar.setTime(incomingDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dayOfWeek = getDayOfWeek();
        
        List<Integer> days = new ArrayList<>();

        for (Calendar calDate : dates) {
            Date theDate = calDate.getTime();
            String day = DateFormatter.dayFormat.format(theDate);
            
            days.add(Integer.parseInt(day));
        }

        StringBuilder calTable = new StringBuilder("<table id=\"calendar\" class=\"table table-striped table-condensed table-hover\">");
        calTable.append("<thead class=\"thead-default\">");
        calTable.append("<tr>");
        calTable.append("<td colspan=\"1\" align=\"left\">");
        String pastMonthDateStr = String.format("%02d", previousMonth);
        String dateLink = incomingYear+pastMonthDateStr;
        
            calTable.append(dateHrefTemplate(new Object[]{path, handle, dateLink}))
                    .append("<i class=\"fa fa-arrow-left\"></i></a>");
        
        calTable.append("</td>");
        calTable.append("<td colspan=\"5\" align=\"center\">");
        calTable.append(Month.of(getMonth() + 1));
        calTable.append(" ");
        calTable.append(getYear());
        calTable.append("</td>");
        calTable.append("<td colspan=\"1\" align=\"right\">");
        
        String nextMonthDateStr = String.format("%02d", nextMonth);
        dateLink = incomingYear+nextMonthDateStr;
        
            calTable.append(dateHrefTemplate(new Object[]{path, handle, dateLink}));
            calTable.append("<i class=\"fa fa-arrow-right\"></i>");
            calTable.append("</a>");
            
        calTable.append("</td>");
        calTable.append("</tr>");

        calTable.append("<tr>");
        for (String dayName : dayNames) {
            if (dayName.length() > 0) {
                calTable.append("<th>");
                calTable.append(dayName);
                calTable.append("</th>");
            }
        }
        calTable.append("</tr>");
        calTable.append("</thead>");

        calTable.append("<tbody>");
        calTable.append("<tr>");
        
        
        if (calendar.getFirstDayOfWeek() == Calendar.MONDAY) {
            if (dayOfWeek == 1) {
                dayOfWeek += 6;                
            } else {
                dayOfWeek--;                
            }
        }
        
        for (int i = 1; i < dayOfWeek; i++) {
            calTable.append("<td></td>");            
        }
        
        dayOfWeek = 0;
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            //start++;
            if (dayOfWeek > 7) {
                calTable.append("</tr><tr>");
            }
            Calendar tempCal = (Calendar) calendar.clone();
            tempCal.set(Calendar.MONTH, getMonth());
            tempCal.set(Calendar.DATE, i);
            tempCal.set(Calendar.YEAR, getYear());
            dayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
            dayOfMonth = tempCal.get(Calendar.DAY_OF_MONTH);
            calTable.append("<td>");
            if (days.contains(dayOfMonth)) {
                //add a padding zero to the month string
                String monthString = String.format("%02d", (getMonth() + 1));                
                String dayString = String.format("%02d", dayOfMonth);                
                String thisDateString = Integer.toString(getYear())
                        .concat(monthString)
                        .concat(dayString);
                calTable.append("<b>");
                calTable.append(dateHrefTemplate(new Object[]{path, handle, thisDateString}));
            }
            calTable.append(Integer.toString(dayOfMonth));
            if (days.contains(dayOfMonth)) {
                calTable.append("</a>");
                calTable.append("</b>");
            }
            calTable.append("</td>");
           
            if(i == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                int tdPad = 6-dayOfWeek;
                for(int t=0; t<=tdPad; t++){
                    calTable.append("<td></td>");
                }
            }
            
            dayOfWeek++;            
        }
        
       
        calTable.append("</tr>");
        calTable.append("</table>");

        return calTable.toString();
    }

}
