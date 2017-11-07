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
package com.hkstlr.reeler.app.control;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class PaginatorTest {
    
    private Paginator cut = new Paginator();
    
    public PaginatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPageSize method, of class Paginator.
     */
    @Test
    public void testGetPageSize() {
        System.out.println("getPageSize");
        int pageSize = 24;
        cut.setPageSize(pageSize);
        int expResult = 24;
        int result = cut.getPageSize();
        assertEquals(expResult, result);
       
    }

     /**
     * Test of getPage method, of class Paginator.
     */
    @Test
    public void testGetPage() {
        System.out.println("getPage");
        Paginator instance = new Paginator();
        int expResult = 1;
        int result = instance.getPage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPage method, of class Paginator.
     */
    @Test
    public void testSetPage() {
        System.out.println("setPage");
        int page = 1;
        Paginator instance = new Paginator();
        instance.setPage(page);
        assertEquals(page, instance.getPage());
    }

    /**
     * Test of getNumberOfPages method, of class Paginator.
     */
    @Test
    public void testGetNumberOfPages() {
        System.out.println("getNumberOfPages");
        Paginator instance = new Paginator(24,1,2);
        int expResult = 1;
        int result = instance.getNumberOfPages();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setNumberOfPages method, of class Paginator.
     */
    @Test
    public void testSetNumberOfPages() {
        System.out.println("setNumberOfPages");
        int numberOfPages = 0;
        Paginator instance = new Paginator();
        instance.setNumberOfPages(numberOfPages);
        assertEquals(numberOfPages, instance.getNumberOfPages());
    }

    /**
     * Test of getNumberOfItems method, of class Paginator.
     */
    @Test
    public void testGetNumberOfItems() {
        System.out.println("getNumberOfItems");
        Paginator instance = new Paginator();
        int expResult = 0;
        int result = instance.getNumberOfItems();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setNumberOfItems method, of class Paginator.
     */
    @Test
    public void testSetNumberOfItems() {
        System.out.println("setNumberOfItems");
        int numberOfItems = 0;
        Paginator instance = new Paginator();
        instance.setNumberOfItems(numberOfItems);
        
    }

    /**
     * Test of getPageFirstItem method, of class Paginator.
     */
    @Test
    public void testGetPageFirstItem_0args() {
        System.out.println("getPageFirstItem");
        Paginator instance = new Paginator();
        instance.setPage(2);
        instance.setPageSize(24);
        int expResult = 25;
        int result = instance.getPageFirstItem();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPageFirstItem method, of class Paginator.
     */
    @Test
    public void testGetPageFirstItem_int_int() {
        System.out.println("getPageFirstItem");
        int page = 2;
        int pageSize = 24;
        int expResult = 25;
        int result = cut.getPageFirstItem(page, pageSize);
        assertEquals(expResult, result);
        
        
        page = 3;
        pageSize = 24;
        cut.setNumberOfItems(50);
        expResult = 49;
        result = cut.getPageFirstItem(page, pageSize);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPageLastItem method, of class Paginator.
     */
    @Test
    public void testGetPageLastItem_0args() {
        System.out.println("getPageLastItem");
        
        cut.setNumberOfItems(50);
        cut.setPageSize(24);
        cut.setPage(2);
        int expResult = 48;
        int result = cut.getPageLastItem();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPageLastItem method, of class Paginator.
     */
    @Test
    public void testGetPageLastItem_3args() {
        System.out.println("getPageLastItem");
        int firstPageItem = 49;
        int pageSize = 24;
        int numberOfItems = 50;
        int expResult = 50;
        int result = cut.getPageLastItem(firstPageItem, pageSize, numberOfItems);
        assertEquals(expResult, result);
        
        cut.setPage(1);
        firstPageItem = 1;
        pageSize = 24;
        numberOfItems = 50;
        expResult = 24;
        result = cut.getPageLastItem(firstPageItem, pageSize, numberOfItems);
        System.out.println(result);
        assertEquals(expResult, result);
        
        cut.setPage(2);
        firstPageItem = 25;
        pageSize = 24;
        numberOfItems = 50;
        expResult = 48;
        result = cut.getPageLastItem(firstPageItem, pageSize, numberOfItems);
        System.out.println(result);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of isHasNextPage method, of class Paginator.
     */
    @Test
    public void testHasNextPage() {
        System.out.println("isHasNextPage");
        Paginator instance = new Paginator(24,3,299);
        boolean expResult = true;
        boolean result = instance.hasNextPage();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of nextPage method, of class Paginator.
     */
    @Test
    public void testNextPage() {
        System.out.println("nextPage");
        Paginator instance = new Paginator(24,2,299);
        instance.nextPage();
        
        assertEquals(instance.getPage(),3);
    }

    /**
     * Test of isHasPreviousPage method, of class Paginator.
     */
    @Test
    public void testHasPreviousPage() {
        System.out.println("isHasPreviousPage");
        Paginator instance = new Paginator(24,2,299);
        
        boolean expResult = true;
        boolean result = instance.hasPreviousPage();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of previousPage method, of class Paginator.
     */
    @Test
    public void testPreviousPage() {
        System.out.println("previousPage");
        Paginator instance = new Paginator(60,3,299);
                
        instance.previousPage();
        
        assertEquals(2, instance.getPage());
        
    }
    
}