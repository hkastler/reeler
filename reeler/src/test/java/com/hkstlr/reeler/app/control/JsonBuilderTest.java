/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author henry.kastler
 */
public class JsonBuilderTest {

    private static final Logger log = Logger.getLogger(JsonBuilderTest.class.getName());

    JsonBuilder cut;
    WeblogEntry weblogEntry;
    String[] skipFields;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.cut = new JsonBuilder();
        try {
            this.weblogEntry = TestSetup.getWeblogEntry();
        } catch (Exception ex) {
            Logger.getLogger(JsonBuilderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.skipFields = new String[]{"id", "pubTime", "updateTime", "dateCreated"};
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toJsonString method, of class JsonBuilder.
     */
    @Test
    public void testToJsonString_Object() {
        System.out.println("toJsonString");
        Object o = weblogEntry;
        JsonBuilder instance = cut;
        String expResult = "testEntryLink";
        String result = instance.toJsonString(weblogEntry);
        //log.info(result);
        JsonParser parser = Json.createParser(new StringReader(result));
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    log.fine(event.toString());
                    break;
                case KEY_NAME:
                   log.fine(event.toString() + " "
                            + parser.getString() + " - ");
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    log.fine(event.toString() + " "
                            + parser.getString());
                    break;
            }
        }
        assertTrue(result.contains(expResult));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toJsonString method, of class JsonBuilder.
     */
    @Test
    public void testToJsonString_Object_StringArr() {
        System.out.println("toJsonString");
        Object o = weblogEntry;
        String[] lskipFields = skipFields;
        JsonBuilder instance = new JsonBuilder();
        String expResult = "";
        String result = instance.toJsonString(o, lskipFields);
        assertTrue(!result.contains("pubTime"));
        // assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toJsonObject method, of class JsonBuilder.
     */
    @Test
    public void testToJsonObject() {
        System.out.println("toJsonObject");
        Object o = weblogEntry;
        //String[] skipFields = new String[]{};
        JsonBuilder instance = cut;
        //JsonObject expResult = null;
        JsonObject result = instance.toJsonObject(o, skipFields);
        //log.info("result:" + result.toString());
        WeblogEntry resultWeblog = new WeblogEntry();
             
        resultWeblog.setAnchor(result.getString("anchor"));
        assertEquals(weblogEntry.getAnchor(), resultWeblog.getAnchor());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toJsonArray method, of class JsonBuilder.
     */
    @Ignore
    @Test
    public void testToJsonArray() {
        System.out.println("toJsonArray");
        Object o = weblogEntry;
        //String[] skipFields = null;
        JsonBuilder instance = new JsonBuilder();
        JsonObject expResult = null;
        JsonObject result = instance.toJsonArray(o, skipFields);
        //log.info("result:" + result);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
