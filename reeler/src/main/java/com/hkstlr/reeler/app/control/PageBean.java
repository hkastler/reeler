/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.app.control;

import javax.faces.bean.ManagedProperty;


/**
 *
 * @author henry.kastler
 */
public class PageBean {
    
    @ManagedProperty(value = "#{param.page}")
    protected Integer pageNum;

    @ManagedProperty(value = "#{param.pageSize}")
    protected Integer pageSize;

    protected Paginator paginator = new Paginator();
    
    public PageBean() {
        //default constructor
    }
    
    public Integer getPageNum() {
        if (this.pageNum == null) {
            this.pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
    
}
