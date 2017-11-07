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
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTest;
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
public class WeblogEntryCommentVisitorTest {

    public static Logger log = Logger.getLogger(WeblogEntryCommentVisitorTest.class.getName());

    User testUser = null;
    Weblog testWeblog = null;
    WeblogCategory testWeblogCategory = null;
    WeblogEntry testWeblogEntry = null;
    WeblogEntryComment testWeblogEntryComment = null;
    WeblogEntryCommentVisitor cut = null;

    public WeblogEntryCommentVisitorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        testUser = TestSetup.getUser();
        testWeblog = TestSetup.getWeblog();
        testWeblogEntry = TestSetup.getWeblogEntry();
        testWeblogEntryComment = new WeblogEntryComment();
        testWeblogEntryComment.setWeblogEntry(testWeblogEntry);
        cut = new WeblogEntryCommentVisitor(false,testWeblogEntryComment);
                
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getWeblogEntryComment method, of class WeblogEntryCommentVisitor.
     */
    @Test
    public void testGetWeblogEntryComment() {
        System.out.println("getWeblogEntryComment");
        System.out.println("testUser:" + testUser.getUserName());
        //WeblogEntryCommentVisitor instance = null;
        WeblogEntryComment expResult = testWeblogEntryComment;
        WeblogEntryComment result = cut.getWeblogEntryComment();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setWeblogEntryComment method, of class WeblogEntryCommentVisitor.
     */
    @Test
    public void testSetWeblogEntryComment() {
        System.out.println("setWeblogEntryComment");
        WeblogEntryComment weblogEntryComment = testWeblogEntryComment;
        //WeblogEntryCommentVisitor instance = null;
        cut.setWeblogEntryComment(weblogEntryComment);
        assertEquals(cut.getWeblogEntryComment(),testWeblogEntryComment);
        
    }

    /**
     * Test of isSpam method, of class WeblogEntryCommentVisitor.
     */
    @Test
    public void testIsSpam() {
        System.out.println("isSpam");
        //WeblogEntryCommentVisitor instance = null;
        boolean expResult = false;
        boolean result = cut.isSpam();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of setSpamAndApproval method, of class WeblogEntryCommentVisitor.
     */
    @Test
    public void testSetSpamAndApproval() {
        System.out.println("setSpamAndApproval");
        //WeblogEntryCommentVisitor instance = null;
        cut.setSpamAndApproval();
        assertEquals(cut.getWeblogEntryComment().getStatus(),WeblogEntryComment.ApprovalStatus.PENDING);
       
        cut.setSpam(true);
        cut.setSpamAndApproval();
        assertEquals(cut.getWeblogEntryComment().getSpam(),true);
        assertEquals(cut.getWeblogEntryComment().getStatus(),WeblogEntryComment.ApprovalStatus.SPAM);
        log.info("testSetSpam:" + cut.getWeblogEntryComment().getSpam());
        
        cut.setApproved(true);
        cut.setSpamAndApproval();
        log.info("comment getApproved:" + cut.getWeblogEntryComment().getStatus().toString());
        assertEquals(cut.getWeblogEntryComment().getSpam(),false);
    }

}
