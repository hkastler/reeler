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
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryTagTest {
    
    WeblogEntryTag cut;
    
    public WeblogEntryTagTest() {
    }

    @Before
    public void setUp() throws Exception {       
        cut = new WeblogEntryTag();
    }
    
    @Test
    public void testWeblogEntryTagEntity() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        TestEntityReflector ter = new TestEntityReflector();
        ter.testEntityClass(cut);
        ter.testTableAnotation(cut.getClass(), "roller_weblogentrytag");
    }
    
    
}
