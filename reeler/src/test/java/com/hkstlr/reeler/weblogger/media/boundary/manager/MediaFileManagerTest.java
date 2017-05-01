/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.media.boundary.manager;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.TestManagerReflector;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author henry.kastler
 */
public class MediaFileManagerTest {
    
    @Mock
    private EntityManager em;
    
    @InjectMocks
    MediaFileManager cut;
    
    
    public MediaFileManagerTest() {
    }
    
     @Before
    public void setUp() throws NamingException {
        
        MockitoAnnotations.initMocks(this);
        
    }

    @Test
    public void testMediaFileManager() {
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }

    /**
     * Test of getEntityManager method, of class MediaFileManager.
     */
    @Test
    public void testGetEntityManager() {
        assertNotNull(cut.getEntityManager());
    }

    /**
     * Test of getDefaultMediaFileDirectory method, of class MediaFileManager.
     */
    @Test
    public void testGetDefaultMediaFileDirectory() {
    }

    /**
     * Test of removeAllFiles method, of class MediaFileManager.
     */
    @Test
    public void testRemoveAllFiles() {
    }

    /**
     * Test of createDefaultMediaFileDirectory method, of class MediaFileManager.
     */
    @Test
    public void testCreateDefaultMediaFileDirectory() {
    }

    /**
     * Test of getMediaFileByOriginalPath method, of class MediaFileManager.
     */
    @Test
    public void testGetMediaFileByOriginalPath() {
    }

    /**
     * Test of removeMediaFile method, of class MediaFileManager.
     */
    @Test
    public void testRemoveMediaFile() {
    }

    /**
     * Test of createMediaFile method, of class MediaFileManager.
     */
    @Test
    public void testCreateMediaFile() {
    }
    
}
