/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.pings.boundary.PingTargetManager;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author henry.kastler
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WebloggerConfig.class)
public class WeblogManagerTest {

    private static final Logger log = Logger.getLogger(WeblogManagerTest.class.getName());

    @Mock
    private EntityManager em;

    @InjectMocks
    private WeblogManager cut;
    
    @Mock
    private WeblogPermissionManager wpm;
    
    @Mock
    private PingTargetManager ptm;

   

    public WeblogManagerTest() {
    }

    @Before
    public void setUp() throws NamingException {
        
        PowerMockito.mockStatic(WebloggerConfig.class);
        MockitoAnnotations.initMocks(this);
        this.cut.em = em;
        this.cut.weblogPermissionManager = wpm;
        this.cut.weblogPermissionManager.em = em;
        this.cut.pingTargetManager = ptm;
        this.cut.pingTargetManager.em = em;
    }

    @Test
    public void testWeblogManager() {
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
        verify(this.cut.em, times(1)).persist(weblog);
    }

    /*@Test
    public void testAddWeblog() throws Exception {
    System.out.println("addWeblog");
    Weblog weblog = TestSetup.getWeblog();
    User user = TestSetup.getUser();
    cut.addWeblog(weblog, user);
    //doNothing().when(cut.weblogPermissionManager).grantWeblogPermission(weblog, user, null,false);
    //when(WebloggerConfig.getProperty("newuser.categories")).thenReturn("General, Technology");
    //verify(this.cut.em, times(1)).merge(weblog);
    }*/
    @Test
    public void testAddWeblog() throws Exception {
        System.out.println("addWeblog");
        Weblog weblog = TestSetup.getWeblog();
        User user = TestSetup.getUser();
        cut.addWeblog(weblog, user);
        List<String> actions = new ArrayList<>();
        
        
        TypedQuery mockQuery = mock(TypedQuery.class);        
        WeblogPermission perm = new WeblogPermission(weblog, actions);
        when(mockQuery.getSingleResult()).thenReturn(perm);
        String qName = "WeblogPermission.getByUserName&WeblogIdIncludingPending";
        when(this.cut.weblogPermissionManager.em.createNamedQuery(qName)).thenReturn(mockQuery);    
        
              
        
        WebloggerConfig config = new WebloggerConfig();
        PowerMockito.whenNew(WebloggerConfig.class).withNoArguments().thenReturn(config);
        when(config.getProperty("newuser.categories")).thenReturn("General,Technology");
             
        List<PingTarget> pings = new ArrayList<>();
        PingTarget pt = new PingTarget();
        pings.add(pt);
        
        
        when(this.cut.pingTargetManager.getCommonPingTargets()).thenReturn(pings);
        
        TypedQuery mockQuery2 = mock(TypedQuery.class);
        when(this.cut.pingTargetManager.em.createNamedQuery(Matchers.anyString())).thenReturn(mockQuery2);
        
        verify(this.cut.em, times(2)).merge(weblog);
     }

}
