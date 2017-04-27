/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author henry.kastler
 */
public class WeblogBookmarkManagerTest {
    
    @Mock
    private EntityManager em;

    @InjectMocks
    private WeblogBookmarkManager cut;

  
    
    public WeblogBookmarkManagerTest() {
    }

   @Before
    public void setUp() throws NamingException {

        MockitoAnnotations.initMocks(this);
        this.cut.em = em;

    }
    
     @Test
    public void testWeblogBookmarkManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }
    
}
