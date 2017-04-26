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
