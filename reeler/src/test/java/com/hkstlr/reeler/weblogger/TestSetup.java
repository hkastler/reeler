/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */
 /*
 * TestUtils.java
 *
 * Created on April 6, 2006, 8:38 PM

originally 
package org.apache.roller.weblogger.TestUtils
freely adapted here
commented out calls to the db
 */
package com.hkstlr.reeler.weblogger;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.InjectMocks;

/**
 *
 * @author henry.kastler
 */
public class TestSetup {

    @InjectMocks
    Weblogger weblogger;

    public User testUser = null;
    public Weblog testWeblog = null;
    public WeblogCategory testWeblogCategory = null;
    public WeblogEntry testWeblogEntry = null;

    // Username prefix we are using (simplifies local testing)
    public static final String JUNIT_PREFIX = "junit_";

    public static User getUser() throws Exception {
        return setupUser("testola");
    }

    /**
     * Convenience method that creates a user and stores it.
     */
    public static User setupUser(String userName) throws Exception {

        // Set local name
        userName = JUNIT_PREFIX + userName;

        User testUser = new User();
        testUser.setUserName(userName);
        testUser.setPassword("password");
        testUser.setScreenName("Test User Screen Name");
        testUser.setFullName("Test User");
        testUser.setEmailAddress("TestUser@dev.null");
        testUser.setLocale("en_US");
        testUser.setTimeZone("America/Chicago");
        testUser.setDateCreated(new java.util.Date());
        testUser.setEnabled(Boolean.TRUE);

        return testUser;
    }

    public static Weblog getWeblog() throws Exception {
        return setupWeblog("test", TestSetup.getUser());
    }

    public static Weblog setupWeblog(String handle, User creator)
            throws Exception {

        Weblog testWeblog = new Weblog();
        testWeblog.setName("Test Weblog");
        testWeblog.setTagline("Test Weblog");
        testWeblog.setHandle(handle);
        testWeblog.setEmailAddress("testweblog@dev.null");
        testWeblog.setEditorPage("editor-text.jsp");
        testWeblog.setBlacklist("");
        testWeblog.setEditorTheme("basic");
        testWeblog.setLocale("en_US");
        testWeblog.setTimeZone("America/Los_Angeles");
        testWeblog.setDateCreated(new java.util.Date());
        testWeblog.setCreator(creator.getUserName());

        return testWeblog;
    }

    public static WeblogEntry getWeblogEntry() throws Exception {
        return setupWeblogEntry("test",
                new WeblogCategory("test category", 0),
                WeblogEntry.PubStatus.PUBLISHED, TestSetup.getWeblog(), TestSetup.getUser());
    }

    /**
     * Convenience method for creating a weblog entry
     */
    public static WeblogEntry setupWeblogEntry(String anchor,
            WeblogCategory cat, WeblogEntry.PubStatus status, Weblog weblog, User user)
            throws Exception {

        WeblogEntry testEntry = new WeblogEntry();
        testEntry.setTitle(anchor);
        testEntry.setLink("testEntryLink");
        testEntry.setText("blah blah entry");
        testEntry.setAnchor(anchor);
        Calendar pubTime = Calendar.getInstance(weblog.getTimeZoneInstance(), weblog.getLocaleInstance());
        pubTime.setTime(new Date());
        testEntry.setPubTime(pubTime);
        testEntry.setUpdateTime(new java.sql.Timestamp(new java.util.Date()
                .getTime()));
        testEntry.setStatus(status.toString());
        testEntry.setWebsite(weblog);
        testEntry.setCreatorUserName(user.getUserName());
        testEntry.setCategory(cat);

        return testEntry;
    }

    public static WeblogEntryComment getWeblogEntryComment(){
    
        WeblogEntryComment wec = new WeblogEntryComment();
        try {
            wec.setWeblogEntry(TestSetup.getWeblogEntry());
        } catch (Exception ex) {
            Logger.getLogger(TestSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wec;
    }

    public static WeblogCategory setupWeblogCategory(String name)
            throws Exception {

        WeblogCategory testWeblogCategory = new WeblogCategory(name, 0);

        return testWeblogCategory;
    }

}
