/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.LoggerExposer;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.themes.entities.WeblogCustomTemplate;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author henry.kastler
 */
@RunWith(Arquillian.class)
public class WeblogManagerIT {
    
@Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "weblog-manager-test.war")
                
                
                .addPackage(AbstractManager.class.getPackage())
                .addPackage("com.hkstlr.reeler.app.control")
                .addPackage("com.hkstlr.reeler.app.entities")                              
                .addClass(LoggerExposer.class)
                     
                
                .addPackage("com.hkstlr.reeler.weblogger.users.boundary.manager")
                .addPackage("com.hkstlr.reeler.weblogger.users.control")
                .addPackage("com.hkstlr.reeler.weblogger.users.entities")
                
                .addPackage("com.hkstlr.reeler.weblogger.pings.boundary")
                .addPackage("com.hkstlr.reeler.weblogger.pings.entities")
                
                .addPackage("com.hkstlr.reeler.weblogger.themes.entities")
                .addPackage("com.hkstlr.reeler.weblogger.themes.control")
                
                
                .addPackage("com.hkstlr.reeler.weblogger.weblogs.boundary.manager")
                .addPackage("com.hkstlr.reeler.weblogger.weblogs.control")
                .addPackage("com.hkstlr.reeler.weblogger.weblogs.control.config")
                .addPackage("com.hkstlr.reeler.weblogger.weblogs.control.entitylisteners")
                .addPackage("com.hkstlr.reeler.weblogger.weblogs.entities")
                
                .addPackage("com.hkstlr.reeler.weblogger.media.boundary.manager")
                .addPackage("com.hkstlr.reeler.weblogger.media.entities")
                
                .addPackage("com.hkstlr.reeler.weblogger.plugins.boundary")
                .addPackage("com.hkstlr.reeler.weblogger.plugins.comment.control")
                .addPackage("com.hkstlr.reeler.weblogger.plugins.control")
                .addPackage("com.hkstlr.reeler.weblogger.plugins.entities")
                .addPackage("com.hkstlr.reeler.weblogger.plugins.entry.control")
                
                .addPackage("com.hkstlr.reeler.weblogger.pings.control")
                .addPackage("com.hkstlr.reeler.weblogger.pings.boundary")                
                .addPackage("com.hkstlr.reeler.weblogger.pings.entities")
                
                .addAsWebInfResource("test-persistence-web.xml", "web.xml")
                .addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(new File("/com/hkstlr/reeler/app/control/config/reeler.properties")
                ,"/com/hkstlr/reeler/app/control/config/reeler.properties");
                
    }

    @EJB
    WeblogManager cut;
    
    Logger log = Logger.getLogger(WeblogManagerTest.class.getName());

    @Test
    @InSequence(1)
    public void testCreate() throws Exception {
    	log.log(Level.INFO, "*************************");
    	log.log(Level.INFO, "testCreate");
    	
        String name = "new weblog";
        String handle = "new-handle";
        String emailaddress = "x.y@test.com";
        Date datecreated = new Date();
        // Save a new weblog.        
       // Weblog weblog = new Weblog(name, handle, true, true, true, emailaddress, true, true, datecreated, true, 0, true, 0, true, true);
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
        //testWeblog.setCreator("hkstlr");
        
        log.log(Level.INFO, "id:{0}", testWeblog.getId());
        
        cut.save(testWeblog);
        log.log(Level.INFO,"weblog {0} saved",testWeblog.getId());
        
        // Make sure it was correctly saved.
        try {
            Weblog weblog = cut.findById(testWeblog.getId());
            log.log(Level.INFO, "findingById:{0} success", weblog.getId());
            assertEquals("new-handle", weblog.getHandle());
        } catch (Exception e) {
            System.out.println("error findingById:" + testWeblog.getId());
        }         

    }  
    
}
