/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.app.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class PageBeanTest {
    
    PageBean cut;
    
    public PageBeanTest() {
    }
    
   
    
    @Before
    public void setUp() {
        cut = new PageBean();
    }

    /**
     * Test of getPageNum method, of class PageBean.
     */
    @Test
    public void testGetPageNum() {
        int pageNum = 1;
        cut.setPageNum(pageNum);
        assertEquals(pageNum,(int)cut.getPageNum());
    }

    /**
     * Test of getPageNum method, of class PageBean.
     */
    @Test
    public void testGetPageNumNull() {
        int pageNum = 1;
        cut.setPageNum(null);
        assertEquals(pageNum,(int)cut.getPageNum());
    }

    /**
     * Test of getPageSize method, of class PageBean.
     */
    @Test
    public void testGetPageSize() {
        int pageSize = 15;
        cut.setPageSize(pageSize);
        assertEquals(pageSize,(int)cut.getPageSize());
    }

    

    /**
     * Test of getPaginator method, of class PageBean.
     */
    @Test
    public void testGetPaginator() {
        Paginator pg = new Paginator();
        cut.setPaginator(pg);
        assertEquals(pg,cut.getPaginator());
    }

    
    
}
