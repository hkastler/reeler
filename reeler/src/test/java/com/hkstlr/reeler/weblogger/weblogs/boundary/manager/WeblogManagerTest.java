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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
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

    private WeblogManager cut;
    
    private static final Logger log = Logger.getLogger(WeblogManagerTest.class.getName());

    @Mock
    private EntityManager em;

    
    
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
        this.cut = new WeblogManager();
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

    @Test
    public void testAddWeblog() throws Exception {
        log.info("addWeblog");
        PowerMockito.mockStatic(WebloggerConfig.class);
        Weblog weblog = TestSetup.getWeblog();
        User user = TestSetup.getUser();
       
        List<String> actions = new ArrayList<>();
        
        
        TypedQuery mockQuery = mock(TypedQuery.class);        
        WeblogPermission perm = new WeblogPermission(weblog, actions);
        when(mockQuery.getSingleResult()).thenReturn(perm);
        String qName = "WeblogPermission.getByUserName&WeblogIdIncludingPending";
        when(this.cut.weblogPermissionManager.em.createNamedQuery(qName)).thenReturn(mockQuery);    
       
        when(WebloggerConfig.getProperty("newuser.categories")).thenReturn("General,Technology");
        
        when(WebloggerConfig.getProperty("newuser.blogroll")).thenReturn("\\\n" +
"Apache Software Foundation|http://apache.org,\\\n" +
"Apache Roller Project|http://roller.apache.org,\\\n" +
"Another URL merge match should now be greatly changed|http://hello.org\\\n");
             
        List<PingTarget> pings = new ArrayList<>();
        PingTarget pt = new PingTarget();
        pings.add(pt);
        
        
        when(this.cut.pingTargetManager.getCommonPingTargets()).thenReturn(pings);
        
        TypedQuery mockQuery2 = mock(TypedQuery.class);
        when(this.cut.pingTargetManager.em.createNamedQuery(Matchers.anyString())).thenReturn(mockQuery2);
        cut.addWeblog(weblog, user);
        verify(this.cut.em, times(1)).persist(weblog);
        verify(this.cut.em,times(3)).merge(Matchers.anyObject());
     }

}
