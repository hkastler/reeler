/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryCommentTest {
    
    WeblogEntryComment cut;
    
    public WeblogEntryCommentTest() {
        //
    }

     @Before
    public void setUp() throws Exception {       
        cut = new WeblogEntryComment();
    }
    
    @Test
    public void testWeblogEntryCommentEntity(){
        TestEntityReflector ter = new TestEntityReflector();
        ter.testEntityClass(cut);
    }
    
}
