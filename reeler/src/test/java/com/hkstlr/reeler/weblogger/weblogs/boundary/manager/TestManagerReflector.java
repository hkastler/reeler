/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 *
 * @author henry.kastler
 */
public class TestManagerReflector {

    private static final Logger log = Logger.getLogger(TestManagerReflector.class.getName());

    public TestManagerReflector() {
    }
    
    
    
    public void testManagerClass(Object cut){
               
        Class cutClass = cut.getClass();
        log.fine("class under reflection:" + cutClass.getName());
        Class<?> superclass = cutClass.getSuperclass();              
        assertEquals(superclass.getName(),"com.hkstlr.reeler.app.boundary.manager.AbstractManager");
        Field[] classFields = cutClass.getDeclaredFields();
        //get all the managers, but not EntityManager
        Field[] managerFields = Arrays.stream(classFields)
                                    .filter(f -> 
                                            f.getType().getName().matches("(.*)boundary(.*)Manager(.*)")
                                            )
                                    .toArray(Field[]::new);
        //ensure all the managers have @EJB annotation
        for(Field f : managerFields){
            log.info(f.getType().getName());
            Annotation ejbAnno = f.getDeclaredAnnotation(javax.ejb.EJB.class);
            assertNotNull(ejbAnno);
        }
        
        //ensure the *Manager class has field em
        //and that it has @PersistenceContext annotation
        Field testEm;
        try {
            testEm = cutClass.getDeclaredField("em");
            assertNotNull(testEm);
            Annotation pc = testEm.getDeclaredAnnotation(javax.persistence.PersistenceContext.class);
            assertNotNull(pc);
            //log.info(testEm.getName());
        } catch (NoSuchFieldException | SecurityException ex) {
            fail("em issues?");
            log.log(Level.SEVERE,"em issue:",ex);
        } 
                
        //ensure the *Manager class has @Stateless annotation
        Annotation annEjb;
        annEjb = cutClass.getDeclaredAnnotation(javax.ejb.Stateless.class);
        assertNotNull(annEjb);
                
    }

}
