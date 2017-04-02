/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
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
public class WeblogEntryAnchorBuilderTest {
    
    User testUser = null;
    Weblog testWeblog = null;
    WeblogEntry testWeblogEntry = null;
    WeblogEntryAnchorBuilder cut = null;
    
    
    public WeblogEntryAnchorBuilderTest() {
    }
    
    
    @Before
    public void setUp() throws Exception {
        testUser = TestSetup.setupUser("testerson");
        testWeblog = TestSetup.setupWeblog("testola", testUser);
        testWeblogEntry = new WeblogEntry();
        cut = new WeblogEntryAnchorBuilder();
    }
    
    /**
     * Test of createAnchorBase method, of class WeblogEntryAnchorBuilder.
     */
    @Test
    public void testCreateAnchorBase() {
        System.out.println("createAnchorBase");
        testWeblogEntry.setTitle("Hello? World Foobar Baz Blog Starman? #");
        testWeblogEntry.setText("jdfljalsdjfjlfsd hlsdhfljayu dshdlfhusoe lasd ");
        String expResult = "hello-world-foobar-baz-blog";
        String result = cut.createAnchorBase(testWeblogEntry,5);
        System.out.println("result:" + result);
        assertEquals(expResult, result);
        
        System.out.println("createAnchorBase");
        testWeblogEntry.setTitle("Hello? World Foobar Baz Blog Starman? #");
        testWeblogEntry.setText("jdfljalsdjfjlfsd hlsdhfljayu dshdlfhusoe lasd ");
        expResult = "hello-world-foobar-baz-blog-starman";
        result = cut.createAnchorBase(testWeblogEntry);
        System.out.println("result:" + result);
        assertEquals(expResult, result);
    }
    
}
