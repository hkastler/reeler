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
