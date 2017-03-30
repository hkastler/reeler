/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
public class WeblogEntryTest {
    
    public static Logger log = Logger.getLogger(WeblogEntryTest.class.getName());
    
    public WeblogEntryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void testSetTagToWeblogEntry() throws WebloggerException {
       
        User testUser = new User();
        testUser.setUserName("testUser");
        testUser.setPassword("password");
        testUser.setScreenName("Test User Screen Name");
        testUser.setFullName("Test User");
        testUser.setEmailAddress("TestUser@dev.null");
        testUser.setLocale("en_US");
        testUser.setTimeZone("America/Los_Angeles");
        testUser.setDateCreated(new java.util.Date());
        testUser.setEnabled(Boolean.TRUE);
        
        Weblog testWeblog = new Weblog();
        testWeblog.setName("Test Weblog");
        testWeblog.setTagline("Test Weblog");
        testWeblog.setHandle("test");
        testWeblog.setEmailAddress("testweblog@dev.null");
        testWeblog.setEditorPage("editor-text.jsp");
        testWeblog.setBlacklist("");
        testWeblog.setEditorTheme("basic");
        testWeblog.setLocale("en_US");
        testWeblog.setTimeZone("America/Los_Angeles");
        testWeblog.setDateCreated(new java.util.Date());
        testWeblog.setCreator(testUser.getUserName());
        
        WeblogEntry we = new WeblogEntry();
       
        we.setWebsite(testWeblog);
        String tagNames = "hello world what";
        WeblogEntryTag whatTag = null;
        WeblogEntryTag helloTag = null;
        
        we.setTagsAsString(tagNames);
        
        assertTrue(we.getTags().size() == 3);
        
        Collection<WeblogEntryTag> tags = we.getTags();
        for(WeblogEntryTag tag : tags){
            //log.info("tagName:" + tag.getName());
            if(tag.getName().equals("hello"))
                helloTag = tag;
            if(tag.getName().equals("what"))
                whatTag = tag;
            assertTrue(we.getTags().contains(tag));
        }
        
        we.removeTag(whatTag);
        assertTrue(we.getTags().size()==2);
        assertTrue(!we.getTags().contains(whatTag));
        assertTrue(we.getTags().contains(helloTag));
        
        //we.removeTag(weTag);
        //assertEquals(we, tag.getWeblogEntry());
        //assertTrue(we.getTags().contains(tag));
    }
    
}
