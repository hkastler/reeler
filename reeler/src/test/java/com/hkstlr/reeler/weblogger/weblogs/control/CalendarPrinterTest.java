/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;

/**
 *
 * @author henry.kastler
 */
public class CalendarPrinterTest extends TestCase {
    
    @InjectMocks
    WeblogEntryManager wem;       
    
    User testUser = null;
    Weblog testWeblog = null;
    CalendarPrinter cut = null;
    
    public CalendarPrinterTest() {
    }
    
    
    
    @Before
    public void setUp() throws Exception {
        testUser = TestSetup.setupUser("testerson");
        testWeblog = TestSetup.setupWeblog("testola", testUser);
        cut = new CalendarPrinter();
        cut.init();
    }
    
    @After
    public void tearDown() {
    }

    /**
   
    /**
     * Test of getDayNames method, of class CalendarPrinter.
     */
    @Test
    public void testGetDayNames() {
        System.out.println("getDayNames");
        CalendarPrinter instance = new CalendarPrinter();
        String[] result = instance.getDayNames();
       
        //this method returns a blank space at the beginning
        //so it should be 8 rather than 7
        assertTrue(result.length == 8);
        assertTrue(Arrays.asList(result).contains("Mon"));
        
    }

    
    /**
     * Test of calendarTable method, of class CalendarPrinter.
     */
    @Test
    public void testCalendarTable_String_Weblog() {
        System.out.println("calendarTable");
        String path = "/date/";
        List<Calendar> dates = new ArrayList<>();
        
        String result = "";
        try {
            result = cut.calendarTable(dates,new Date(),"","");
            System.out.println(result);
        } catch (ParseException ex) {
            Logger.getLogger(CalendarPrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertTrue(result.contains("</table>"));
       
    }

    
    
}
