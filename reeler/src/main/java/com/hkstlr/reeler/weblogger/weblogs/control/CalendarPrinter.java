/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.app.control.StringPool;
import java.text.DateFormatSymbols;
import static java.text.MessageFormat.format;
import java.text.ParseException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

/**
 *
 * @author henry.kastler
 */
@Dependent
public class CalendarPrinter {

    private Calendar calendar;

    Date now = new Date();

    
    private int dayOfWeek;

    private static final String TROPEN = "<tr>";
    private static final String TRCLOSE = "</tr>";
    private static final String TDOPEN = "<td>";
    private static final String TDCLOSE = "</td>";

    DateFormatSymbols symbols = new DateFormatSymbols();

    String[] dayNames = symbols.getShortWeekdays();

    public CalendarPrinter() {
        //calendar 
    }    

    @PostConstruct
    public void init() {

        String strYear = DateFormatter.yearFormat.format(now);
        String strMonth = DateFormatter.monthFormat.format(now);

        calendar = new GregorianCalendar(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

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

    private String arrowTemplate(Object[] args) {
        String template = "<a id=\"arrow-{3}\" href=\"{0}/{1}/date/{2}\">"
                + "<i class=\"fa fa-arrow-{3}\"></i></a>";
               
        return format(template, args);
    }
    private String calDateTemplate(Object[] args) {
        String template = "<a href=\"{0}/{1}/date/{2}\">";
                
        return format(template, args);
    }

    private String tdAlignColspanTemplate(Object[] args) {
        String template = "<td align=\"{0}\" colspan=\"{1}\" >";
        String formatted = format(template, args);
        return formatted;
    }

    public String calendarTable(List<Calendar> dates, Date incomingDate, String path, String handle) throws ParseException {

        
        int dayOfMonth;
        
        String incomingMonth = DateFormatter.monthFormat.format(incomingDate);
        Integer intMonth = Integer.parseInt(incomingMonth);
        Integer previousMonth = intMonth - 1;
        Integer nextMonth = intMonth + 1;

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

        StringBuilder calTable = new StringBuilder("<table id=\"calendarTable\" class=\"table table-striped table-condensed table-hover\">");
        calTable.append("<thead class=\"thead-default\">");
        calTable.append(TROPEN);
        calTable.append(tdAlignColspanTemplate(new String[]{"left", "1"}));
        String pastMonthDateStr = String.format("%02d", previousMonth);
        String dateLink = incomingYear + pastMonthDateStr;

        calTable.append(arrowTemplate(new String[]{path, handle, dateLink, "left"}));

        calTable.append(TDCLOSE);
        calTable.append(tdAlignColspanTemplate(new String[]{"center", "5"}));
        calTable.append(Month.of(getMonth() + 1));
        calTable.append(StringPool.SPACE);
        calTable.append(getYear());
        calTable.append(TDCLOSE);
        calTable.append(tdAlignColspanTemplate(new String[]{"right", "1"}));

        String nextMonthDateStr = String.format("%02d", nextMonth);
        dateLink = incomingYear + nextMonthDateStr;

        calTable.append(arrowTemplate(new String[]{path, handle, dateLink, "right"}));

        calTable.append(TDCLOSE);
        calTable.append(TRCLOSE);

        calTable.append(TROPEN);
        for (String dayName : dayNames) {
            if (dayName.length() > 0) {
                calTable.append("<th>");
                calTable.append(dayName);
                calTable.append("</th>");
            }
        }
        calTable.append(TRCLOSE);
        calTable.append("</thead>");

        calTable.append("<tbody>");
        calTable.append(TROPEN);

        if (calendar.getFirstDayOfWeek() == Calendar.MONDAY) {
            if (dayOfWeek == 1) {
                dayOfWeek += 6;
            } else {
                dayOfWeek--;
            }
        }

        for (int i = 1; i < dayOfWeek; i++) {
            calTable.append(TDOPEN.concat(TDCLOSE));
        }

        dayOfWeek = 0;
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            //start++;
            if (dayOfWeek > 7) {
                calTable.append(TRCLOSE.concat(TROPEN));
            }
            Calendar tempCal = (Calendar) calendar.clone();
            tempCal.set(Calendar.MONTH, getMonth());
            tempCal.set(Calendar.DATE, i);
            tempCal.set(Calendar.YEAR, getYear());
            dayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
            dayOfMonth = tempCal.get(Calendar.DAY_OF_MONTH);
            calTable.append(TDOPEN);
            if (days.contains(dayOfMonth)) {
                //add a padding zero to the month string
                String monthString = String.format("%02d", (getMonth() + 1));
                String dayString = String.format("%02d", dayOfMonth);
                String thisDateString = Integer.toString(getYear())
                        .concat(monthString)
                        .concat(dayString);
                calTable.append("<b>");
                calTable.append(calDateTemplate(new String[]{path, handle, thisDateString}));
            }
            calTable.append(Integer.toString(dayOfMonth));
            if (days.contains(dayOfMonth)) {
                calTable.append("</a>");
                calTable.append("</b>");
            }
            calTable.append(TDCLOSE);

            if (i == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                int tdPad = 6 - dayOfWeek;
                for (int t = 0; t <= tdPad; t++) {
                    calTable.append(TDOPEN.concat(TDCLOSE));
                }
            }

            dayOfWeek++;
        }

        calTable.append(TRCLOSE);
        calTable.append("</table>");

        return calTable.toString();
    }

}
