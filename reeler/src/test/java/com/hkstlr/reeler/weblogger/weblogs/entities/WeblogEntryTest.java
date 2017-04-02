/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.util.Collection;
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
    
    User testUser = null;
    Weblog testWeblog = null;
    WeblogEntry cut = null;
    
    public WeblogEntryTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        testUser = TestSetup.setupUser("testerson");
        testWeblog = TestSetup.setupWeblog("testola", testUser);
        cut = new WeblogEntry();
    }
    
    @Test 
    public void testSetTagToWeblogEntry() throws WebloggerException {
              
        cut.setWebsite(testWeblog);
        String tagNames = "hello world what";
        WeblogEntryTag whatTag = null;
        WeblogEntryTag helloTag = null;
        
        cut.setTagsAsString(tagNames);
        
        assertTrue(cut.getTags().size() == 3);
        
        Collection<WeblogEntryTag> tags = cut.getTags();
        for(WeblogEntryTag tag : tags){
            //log.info("tagName:" + tag.getName());
            if(tag.getName().equals("hello"))
                helloTag = tag;
            if(tag.getName().equals("what"))
                whatTag = tag;
            assertTrue(cut.getTags().contains(tag));
        }
        
        cut.removeTag(whatTag);
        assertTrue(cut.getTags().size()==2);
        assertTrue(!cut.getTags().contains(whatTag));
        assertTrue(cut.getTags().contains(helloTag));
        
        //cut.removeTag(cutTag);
        //assertEquals(cut, tag.getWeblogEntry());
        //assertTrue(cut.getTags().contains(tag));
    }
    
    
    
}
