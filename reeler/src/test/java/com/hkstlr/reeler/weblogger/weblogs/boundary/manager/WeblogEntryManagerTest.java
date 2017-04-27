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

    /**
     * Test of save method, of class WeblogEntryManager.
     */
    @Test
    public void testSave() throws Exception {
    }

    /**
     * Test of persist method, of class WeblogEntryManager.
     */
    @Test
    public void testPersist() throws Exception {
    }

    /**
     * Test of edit method, of class WeblogEntryManager.
     */
    @Test
    public void testEdit() throws Exception {
    }

    /**
     * Test of remove method, of class WeblogEntryManager.
     */
    @Test
    public void testRemove() throws Exception {
    }

    /**
     * Test of removeAll method, of class WeblogEntryManager.
     */
    @Test
    public void testRemoveAll() throws Exception {
    }

    /**
     * Test of find method, of class WeblogEntryManager.
     */
    @Test
    public void testFind() throws Exception {
    }

    /**
     * Test of findById method, of class WeblogEntryManager.
     */
    @Test
    public void testFindById() throws Exception {
    }

    /**
     * Test of findByField method, of class WeblogEntryManager.
     */
    @Test
    public void testFindByField() throws Exception {
    }

    /**
     * Test of findAll method, of class WeblogEntryManager.
     */
    @Test
    public void testFindAll() throws Exception {
    }

    /**
     * Test of findRange method, of class WeblogEntryManager.
     */
    @Test
    public void testFindRange() throws Exception {
    }

    /**
     * Test of count method, of class WeblogEntryManager.
     */
    @Test
    public void testCount() throws Exception {
    }

    /**
     * Test of getNamedQuery method, of class WeblogEntryManager.
     */
    @Test
    public void testGetNamedQuery_String() throws Exception {
    }

    /**
     * Test of getNamedQuery method, of class WeblogEntryManager.
     */
    @Test
    public void testGetNamedQuery_String_Class() throws Exception {
    }

    /**
     * Test of getNamedQueryCommitFirst method, of class WeblogEntryManager.
     */
    @Test
    public void testGetNamedQueryCommitFirst() throws Exception {
    }

    /**
     * Test of getWeblogEntryByHandleAndAnchor method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntryByHandleAndAnchor() throws Exception {
    }

    /**
     * Test of getWeblogEntriesForWeblog method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntriesForWeblog() throws Exception {
    }

    /**
     * Test of findByPinnedToMain method, of class WeblogEntryManager.
     */
    @Test
    public void testFindByPinnedToMain() throws Exception {
    }

    /**
     * Test of getWeblogEntry method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntry() throws Exception {
    }

    /**
     * Test of findForEdit method, of class WeblogEntryManager.
     */
    @Test
    public void testFindForEdit() throws Exception {
    }

    /**
     * Test of getWeblogEntriesByCategoryName method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntriesByCategoryName() throws Exception {
    }

    /**
     * Test of getWeblogEntriesByCategoryNameAndWeblog method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntriesByCategoryNameAndWeblog() throws Exception {
    }

    /**
     * Test of getWeblogEntriesByDateAndWeblog method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntriesByDateAndWeblog() throws Exception {
    }

    /**
     * Test of getWeblogEntryDatesForCalendar method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntryDatesForCalendar() throws Exception {
    }

    /**
     * Test of getLatestEntryForWeblog method, of class WeblogEntryManager.
     */
    @Test
    public void testGetLatestEntryForWeblog() throws Exception {
    }

    /**
     * Test of saveWeblogEntry method, of class WeblogEntryManager.
     */
    @Test
    public void testSaveWeblogEntry() throws Exception {
    }

    /**
     * Test of removeWeblogEntry method, of class WeblogEntryManager.
     */
    @Test
    public void testRemoveWeblogEntry() throws Exception {
    }

    /**
     * Test of getTags method, of class WeblogEntryManager.
     */
    @Test
    public void testGetTags() throws Exception {
    }

    /**
     * Test of getWeblogEntryCountForWeblog method, of class WeblogEntryManager.
     */
    @Test
    public void testGetWeblogEntryCountForWeblog() throws Exception {
    }

    /**
     * Test of getPaginatedEntries method, of class WeblogEntryManager.
     */
    @Test
    public void testGetPaginatedEntries() throws Exception {
    }

    /**
     * Test of getAllEntriesPaginated method, of class WeblogEntryManager.
     */
    @Test
    public void testGetAllEntriesPaginated() throws Exception {
    }

   
    
}
