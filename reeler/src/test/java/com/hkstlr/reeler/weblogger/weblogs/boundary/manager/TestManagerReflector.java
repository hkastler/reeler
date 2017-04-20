/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author henry.kastler
 */
public class TestManagerReflector {

    private static final Logger log = Logger.getLogger(TestManagerReflector.class.getName());

    public TestManagerReflector() {
    }
    
    
    
    public void testManagerClass(Object cut){
        //WeblogManager weblogManager = new WeblogManager();
        
        Class cutClass = cut.getClass();
        log.info("class under reflection:" + cutClass.getName());
        Class<?> superclass = cutClass.getSuperclass();
        //log.info("superclass under reflection:" + superclass.getName());        
        assertEquals(superclass.getName(),"com.hkstlr.reeler.app.boundary.manager.AbstractManager");
        /*Method[] superclassMethods = superclass.getDeclaredMethods();
        for(Method m : superclassMethods){
        log.info(m.getName());
        }*/
        
        
        Field[] classFields = cutClass.getDeclaredFields();
        //get all the managers, but not EnitityManager
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
        
        /*Annotation[] anns = cutClass.getAnnotations();
        for(Annotation ann : anns){
        log.info(ann.annotationType().getName());
        }*/
        
        //ensure the *Manager class has @Stateless annotation
        Annotation annEjb;
        annEjb = cutClass.getDeclaredAnnotation(javax.ejb.Stateless.class);
        assertNotNull(annEjb);
        //log.info(annEjb.annotationType().getName());
        
        //Type genericSuperclass = cutClass.getGenericSuperclass();        
        //log.info("genericSuperclass:" + genericSuperclass.getClass().getName());
        
    }

}
