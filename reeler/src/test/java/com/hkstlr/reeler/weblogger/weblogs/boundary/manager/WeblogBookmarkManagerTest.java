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
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.MockitoAnnotations.initMocks;

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

        initMocks(this);
        this.cut.em = em;

    }
    
     @Test
    public void testWeblogBookmarkManager(){
        //WeblogManager weblogManager = new WeblogManager();
        TestManagerReflector tmr = new TestManagerReflector();
        tmr.testManagerClass(cut);
    }
    
}
