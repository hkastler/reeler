/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.StringPool;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javassist.Modifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author henry.kastler
 */
public class TestEntityReflector {

    private static final Logger log = Logger.getLogger(TestEntityReflector.class.getName());

    public TestEntityReflector() {
    }

    public void testEntityAnnotation(Class cutClass) {
        Annotation annEntity;
        annEntity = cutClass.getDeclaredAnnotation(javax.persistence.Entity.class);
        assertNotNull(annEntity);
    }

    public void testTableAnotation(Class cutClass, String expectedName) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Annotation annTable;
        annTable = cutClass.getDeclaredAnnotation(javax.persistence.Table.class);
        assertNotNull(annTable);
        //this might be a useless test for Optional here in this method
        //but I thought about it while writing it and it maybe useful somewhere else
        Optional<Method> maybeName = Arrays.stream(annTable.annotationType().getDeclaredMethods())
                                    .findFirst()
                                    .filter(m -> m.getName().equals("name"));
        
       if(maybeName.isPresent()){            
            Method name = maybeName.get();
            Object nameValue = name.invoke(annTable, (Object[]) null);
            log.info(name.getName() + StringPool.COLON + nameValue);
            assertEquals(expectedName, nameValue.toString());
       }
        
        
        
        
        
    }

    public void testEntityClass(Object cut) {
        //WeblogManager weblogManager = new WeblogManager();

        Class cutClass = cut.getClass();
        log.info("class under reflection:" + cutClass.getName());
        Class<?> superclass = cutClass.getSuperclass();
        //log.info("superclass under reflection:" + superclass.getName());        
        assertEquals("com.hkstlr.reeler.app.entities.AbstractEntity", superclass.getName());

        //ensure the entity class has @Entity annotation
        testEntityAnnotation(cutClass);

        Field[] classFields = cutClass.getDeclaredFields();

        //ensure all the fields have the right annotations
        //if the field is not transient, and does not have the OneToMany annotation then it must have 
        //either the @Column or @JoinColumn annotation
        for (Field f : classFields) {
            log.info("field:" + f.getName());
            boolean isTransient = Modifier.isTransient(f.getModifiers());
            if (!isTransient) {
                Annotation columnAnno = f.getDeclaredAnnotation(javax.persistence.Column.class);
                Annotation joinColumnAnno = f.getDeclaredAnnotation(javax.persistence.JoinColumn.class);
                Annotation oneToManyAnno = f.getDeclaredAnnotation(javax.persistence.OneToMany.class);
                if (oneToManyAnno == null) {
                    boolean hasColumnAnno = columnAnno != null;
                    boolean hasJoinColumnAnno = joinColumnAnno != null;
                    assertTrue(hasColumnAnno || hasJoinColumnAnno);

                    //if there's a @Column anno                    
                    if (hasColumnAnno) {
                        for (Method method : columnAnno.annotationType().getDeclaredMethods()) {
                            //log.info("method:" + method.getName());
                            //make sure there's a name
                            if ("name".equals(method.getName())) {
                                try {
                                    Object value = method.invoke(columnAnno, (Object[]) null);
                                    assertTrue(!value.toString().isEmpty());
                                    //log.info("value:" + value.toString());

                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    log.log(Level.SEVERE, null, ex);
                                }
                            }
                            //find if there's a length
                            //and if so ensure @Size
                            //if it's a string field
                            //length will return on datetime
                            if ("length".equals(method.getName()) && "java.lang.String".equals(f.getType().getName())) {
                                try {
                                    Object value = method.invoke(columnAnno, (Object[]) null);
                                    //log.info("value:" + value.toString());
                                    if (Boolean.parseBoolean(value.toString()) == false) {
                                        Annotation sizeAnno = f.getDeclaredAnnotation(javax.validation.constraints.Size.class);
                                        assertNotNull(sizeAnno);
                                    }

                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    log.log(Level.SEVERE, null, ex);
                                }
                            }

                            //find if it's nullable = false
                            //and if so ensure @NotNull
                            if ("nullable".equals(method.getName())) {
                                try {
                                    Object value = method.invoke(columnAnno, (Object[]) null);
                                    //log.info("value:" + value.toString());
                                    if (Boolean.parseBoolean(value.toString()) == false) {
                                        Annotation notNullAnno = f.getDeclaredAnnotation(javax.validation.constraints.NotNull.class);
                                        assertNotNull(notNullAnno);
                                    }

                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    log.log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    }
                }
            }
        }

    }

}
