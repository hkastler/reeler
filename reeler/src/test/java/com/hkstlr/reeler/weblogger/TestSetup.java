/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import javax.inject.Inject;
import org.mockito.InjectMocks;

/**
 *
 * @author henry.kastler
 */
public class TestSetup {
    
    @InjectMocks
    Weblogger weblogger;
    
    User testUser = null;
    Weblog testWeblog = null;
    
    
    // Username prefix we are using (simplifies local testing)
    public static final String JUNIT_PREFIX = "junit_";
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

        // store the user
        //UserManager mgr = weblogger.getUserManager();
        //mgr.addUser(testUser);

        // query for the user to make sure we return the persisted object
        //User user = mgr.getUserByUserName(userName);

        //if (user == null) {
        //    throw new WebloggerException("error inserting new user");
       // }

        return testUser;
    }
    
     public static User setupWeblog(String userName) throws Exception {

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

        // store the user
        //UserManager mgr = weblogger.getUserManager();
        //mgr.addUser(testUser);

        // query for the user to make sure we return the persisted object
        //User user = mgr.getUserByUserName(userName);

        //if (user == null) {
        //    throw new WebloggerException("error inserting new user");
       // }

        return testUser;
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

        // add weblog
        //WeblogManager mgr = WebloggerFactory.getWeblogger().getWeblogManager();
        //mgr.addWeblog(testWeblog);

        // flush to db
        //WebloggerFactory.getWeblogger().flush();

        // query for the new weblog and return it
        //Weblog weblog = mgr.getWeblogByHandle(handle);

        //if (weblog == null) {
        //    throw new WebloggerException("error setting up weblog");
        //}

        return testWeblog;
    }
    
}
