/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.weblogs.control.TagStat;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryManagerTest {

    private static final Logger LOG = Logger.getLogger(WeblogEntryManagerTest.class.getName());
    
    WeblogEntryManager cut;
    
    public WeblogEntryManagerTest() {
    }
    
       
    @Before
    public void setUp() {
        this.cut = new WeblogEntryManager();
        this.cut.em = mock(EntityManager.class);
    }
    
    @Test
    public void testWeblogEntryManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }
    
    /**
     * Test of create method, of class WeblogEntryManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        WeblogEntry weblogEntry = TestSetup.getWeblogEntry();
           
        cut.create(weblogEntry);
        verify(this.cut.em,times(1)).persist(weblogEntry);
    }

   
    
}
