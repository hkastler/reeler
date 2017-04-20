/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author henry.kastler
 */
public class WeblogManagerTest {

    private static final Logger log = Logger.getLogger(WeblogManagerTest.class.getName());
    
    WeblogManager cut;
    
    public WeblogManagerTest() {
    }
       
    @Before
    public void setUp() throws NamingException {
        
        this.cut = new WeblogManager();
        this.cut.em = mock(EntityManager.class);
        
    }
        
    @Test
    public void testWeblogManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }

    /**
     * Test of create method, of class WeblogManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Weblog weblog = TestSetup.getWeblog();
           
        cut.create(weblog);
        verify(this.cut.em,times(1)).persist(weblog);
    }

   
    
}
