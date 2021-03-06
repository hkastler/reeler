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
package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
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
    public void testToJsonString_Object() throws Exception {
        System.out.println("toJsonString");
        Object o = weblogEntry;
        JsonBuilder instance = cut;
        String expResult = "testEntryLink";
        String result = instance.toJsonString(weblogEntry);
        log.info(result);
        User u = TestSetup.getUser();
        UserRole ur = new UserRole("tester", u.getUserName());
        Set<UserRole> roles = new HashSet<>();
        roles.add(ur);
        u.setRoles(roles);
        log.info(cut.toJsonString(u));
        log.info(cut.toJsonString(ur));
        
        /*JsonParser parser = Json.createParser(new StringReader(result));
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
        }*/
        assertTrue(result.contains(expResult));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toJsonString method, of class JsonBuilder.
     */
    @Test
    public void testToJsonString_Object_StringArr() {
        System.out.println("testToJsonString_Object_StringArr");
        Object o = weblogEntry;
        String[] lskipFields = this.skipFields;
        JsonBuilder instance = new JsonBuilder();
        String expResult = "";
        String result = instance.toJsonString(o, lskipFields);
        log.info(result);
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
        WeblogEntry o = weblogEntry;
        //String[] skipFields = new String[]{};
        JsonBuilder instance = cut;
        //JsonObject expResult = null;
        log.info(cut.toJsonObject(weblogEntry.getWebsite(), skipFields).toString());
        JsonObject result = instance.toJsonObject(o, skipFields);
        //log.info("result:" + result.toString());
        WeblogEntry resultWeblog = new WeblogEntry();
             
        resultWeblog.setAnchor(result.getString("anchor"));
        assertEquals(weblogEntry.getAnchor(), resultWeblog.getAnchor());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    

}
