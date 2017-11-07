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

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryTest {
    
    public static Logger log = Logger.getLogger(WeblogEntryTest.class.getName());
    
    static User testUser = null;
    static Weblog testWeblog = null;
    static WeblogEntry cut = null;
    
    public WeblogEntryTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        testUser = TestSetup.setupUser("testerson");
        testWeblog = TestSetup.setupWeblog("testola", testUser);
        cut = new WeblogEntry();
    }
    
    @Test
    public void testWeblogEntryEntity() throws Exception{
        try {
            TestEntityReflector ter = new TestEntityReflector();
            ter.testEntityClass(cut);
            ter.testTableAnotation(cut.getClass(), "weblogentry");
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(WeblogEntryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cut.setAnchor("anchor");
        cut.setPublishEntry(true);
        WeblogEntry we = new WeblogEntry(cut);
        assertTrue(we.getAnchor().equals(cut.getAnchor()));
        assertTrue(we.isPublishEntry() == cut.isPublishEntry());
        
        cut.setData(TestSetup.getWeblogEntry());
        assertTrue(cut.getWebsite().getName().equals("Test Weblog"));
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
        cut.setTags((List)tags);
        assertTrue(cut.getTags().size()==2);
        
        String tagString = cut.getTagsAsString();
        assertTrue(tagString.contains("hello"));
        
        cut.addTag("");
        assertTrue(cut.getTags().size()==2);
        
        cut.setTagsAsString("hello");
        assertTrue(cut.getTags().size()==1);
        
        cut.addTag("hello");
        assertTrue(cut.getTags().size()==1);
        
        cut.addTag("baz");
        assertTrue(cut.getTags().size()==2);
        
        cut.setTagsAsString("");
        assertTrue(cut.getTags().isEmpty());
        //cut.removeTag(cutTag);
        //assertEquals(cut, tag.getWeblogEntry());
        //assertTrue(cut.getTags().contains(tag));
    }
    
    
    
}
