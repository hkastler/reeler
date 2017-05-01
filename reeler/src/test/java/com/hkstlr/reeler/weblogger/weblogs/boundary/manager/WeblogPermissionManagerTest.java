/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author henry.kastler
 */
public class WeblogPermissionManagerTest {
    
    @Mock
    private EntityManager em;

    @InjectMocks
    private WeblogPermissionManager cut;

  
    
    public WeblogPermissionManagerTest() {
    }

   @Before
    public void setUp() throws NamingException {

        MockitoAnnotations.initMocks(this);
        this.cut.em = em;

    }
    
     @Test
    public void testWeblogPermissionManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
        
         
    }
    
    @Test
    public void testGetWeblogPermission() throws Exception {
        System.out.println("getWeblogPermission");
        Weblog weblog = TestSetup.getWeblog();
        TypedQuery<WeblogPermission> q  = mock(TypedQuery.class);
        
        when(q.getSingleResult()).thenReturn(new WeblogPermission());
        
        
        
    }
    
}
