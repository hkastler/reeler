/*
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
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class WeblogTest {

    public static Logger log = Logger.getLogger(WeblogEntryTest.class.getName());

    static User testUser = null;
    static Weblog cut = null;

    public WeblogTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            testUser = TestSetup.setupUser("testerson");
            //testWeblog = TestSetup.setupWeblog("testola", testUser);
            cut = new Weblog();
            cut.setName("Test Weblog");
            cut.setTagline("Test Weblog");
            cut.setHandle("test");
            cut.setEmailAddress("testweblog@dev.null");
            cut.setEditorPage("editor-text.jsp");
            cut.setBlacklist("");
            cut.setEditorTheme("basic");
            cut.setLocale("en_US");
            cut.setTimeZone("America/Los_Angeles");
            cut.setDateCreated(new java.util.Date());
            cut.setCreator(testUser.getUserName());
        } catch (Exception ex) {
            Logger.getLogger(WeblogTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testWeblogEntity(){
        TestEntityReflector ter = new TestEntityReflector();
        ter.testEntityClass(cut);
    }
    
    /*@Test
    public void testGetClassFields() throws ClassNotFoundException {
    System.out.println("classFields");
    //Weblog instance = new Weblog();
    Class weblogClass = Class.forName(Weblog.class.getName());
    
    log.info("getting fields");
    Field[] fields = weblogClass.getDeclaredFields();
    log.info(Integer.toString(fields.length));
    for(Field field : fields){
    log.info("field:" + field.getName());
    log.info("fieldType:" + field.getType().getName());
    }
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
    }*/

    /**
     * Test of getLocale method, of class Weblog.
     */
    @Test
    public void testGetLocale() {
        System.out.println("getLocale");
        //Weblog instance = new Weblog();
        String expResult = "en_US";
        String result = cut.getLocale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

   

    /**
     * Test of getTimeZone method, of class Weblog.
     */
    @Test
    public void testGetTimeZone() {
        System.out.println("getTimeZone");
        //Weblog instance = new Weblog();
        String expResult = "America/Los_Angeles";
        String result = cut.getTimeZone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    /**
     * Test of getLocaleInstance method, of class Weblog.
     */
    @Test
    public void testGetLocaleInstance() {
        System.out.println("getLocaleInstance");
        Weblog instance = cut;
        Locale expResult = new Locale("en","US");
        Locale result = instance.getLocaleInstance();
        assertEquals(expResult, result);
        instance.setLocale("");
        //result = cut.getLocaleInstance();
        result = instance.getLocaleInstance();
        assertEquals(expResult, result);
        log.info("result:" + result.getDisplayCountry());
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeZoneInstance method, of class Weblog.
     */
    @Test
    public void testGetTimeZoneInstance() {
        System.out.println("getTimeZoneInstance");
        Weblog instance = cut;
        TimeZone expResult = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone result = instance.getTimeZoneInstance();
        assertEquals(expResult, result);
        cut.setTimeZone("");
        result = cut.getTimeZoneInstance();
        log.info("result:" + result.getID());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCalendarInstance method, of class Weblog.
     */
    @Test
    public void testGetCalendarInstance() {
        System.out.println("getCalendarInstance");
        Weblog instance = cut;
        Calendar expResult = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"), new Locale("en", "US"));
        //expResult.set(2017, 1, 1, 0, 0, 0);
        Calendar result = instance.getCalendarInstance();
        //result.set(2017,1,1,0,0,0);
        //assertTrue(result.compareTo(expResult) == 1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

   
}
