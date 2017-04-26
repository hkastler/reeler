/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.weblogger.TestSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author henry.kastler
 */
public class WeblogCategoryTest {
    
    WeblogCategory cut;
    
    public WeblogCategoryTest() {
        //constructor
    }
    
    @Before
    public void setUp() throws Exception {       
        cut = new WeblogCategory();
    }
    
    @Test
    public void testWeblogCategoryEntity(){
        TestEntityReflector ter = new TestEntityReflector();
        ter.testEntityClass(cut);
    }
    
    @Test
    public void testCalculatePosition() throws Exception{
        
        Weblog weblog = TestSetup.getWeblog();
          
        WeblogCategory newCat = new WeblogCategory(weblog, "Name", "Description", "Image");
        
        List<WeblogCategory> cats = new ArrayList<>();
        cats.add(newCat);
        weblog.setWeblogCategories(cats);
        cut.setWeblog(weblog);
        cut.calculatePosition(weblog.getWeblogCategories().size());
        assertEquals(1, cut.getPosition());
        
        WeblogCategory newCat2 = new WeblogCategory(weblog, "Name", "Description", "Image");
        cats.add(newCat);
        cats.add(newCat2);
        weblog.setWeblogCategories(cats);
        cut.setWeblog(weblog);
        cut.calculatePosition(weblog.getWeblogCategories().size());
        assertEquals(2, cut.getPosition());
    }

    
    
}
