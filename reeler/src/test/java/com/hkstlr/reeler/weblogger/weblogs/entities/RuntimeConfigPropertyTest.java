/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class RuntimeConfigPropertyTest {

    RuntimeConfigProperty cut;

    public RuntimeConfigPropertyTest() {
    }

    @Before
    public void setUp() throws Exception {
        cut = new RuntimeConfigProperty();
    }

    @Test
    public void testWeblogCategoryEntity() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //not a good candidate for this
        TestEntityReflector ter = new TestEntityReflector();
        Class cutClass = cut.getClass();
        ter.testEntityAnnotation(cutClass);
        ter.testTableAnotation(cutClass, "roller_properties");
    }

}
