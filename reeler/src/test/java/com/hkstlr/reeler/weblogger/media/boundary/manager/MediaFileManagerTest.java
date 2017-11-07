/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
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
