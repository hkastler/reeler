/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.TestSetup;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author henry.kastler
 */
public class WeblogManagerTest {

    private static final Logger log = Logger.getLogger(WeblogManagerTest.class.getName());

    @Mock
    private EntityManager em;

    @InjectMocks
    private WeblogManager cut;

    @InjectMocks
    private WeblogPermissionManager weblogPermissionManager;

    @Mock
    private WebloggerConfig webloggerConfig;

    public WeblogManagerTest() {
    }

    @Before
    public void setUp() throws NamingException {

        MockitoAnnotations.initMocks(this);
        this.cut.em = em;
        this.cut.weblogPermissionManager = weblogPermissionManager;
        this.cut.weblogPermissionManager.em = em;

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
    List<String> actions = new ArrayList<>();
    TypedQuery weblogPermissionQuery = mock(TypedQuery.class);
    
    weblogPermissionQuery.setParameter(1, user.getUserName());
    weblogPermissionQuery.setParameter(2, weblog.getHandle());
    WeblogPermission perm = new WeblogPermission(weblog, actions);
    when(weblogPermissionQuery.getSingleResult()).thenReturn(perm);
    String qName = "WeblogPermission.getByUserName&WeblogIdIncludingPending";
    //when(this.cut.weblogPermissionManager.em.createNamedQuery(qName)).thenReturn(weblogPermissionQuery);
    WeblogPermissionManager wpm = Mockito.spy(this.cut.weblogPermissionManager);
    doNothing().when(wpm).grantWeblogPermission(weblog, user, actions, false);
    //when(weblogPermissionQuery.getResultList()).thenReturn();
    verify(this.cut.em, times(1)).merge(weblog);
    }*/

}
