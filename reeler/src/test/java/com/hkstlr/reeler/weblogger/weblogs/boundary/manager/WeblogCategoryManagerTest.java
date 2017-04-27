/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author henry.kastler
 */
public class WeblogCategoryManagerTest {
    
    @Mock
    private EntityManager em;

    @InjectMocks
    private WeblogCategoryManager cut;

  
    
    public WeblogCategoryManagerTest() {
    }

   @Before
    public void setUp() throws NamingException {

        MockitoAnnotations.initMocks(this);
        this.cut.em = em;

    }
    
     @Test
    public void testWeblogCategoryManagerManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }
    
}
