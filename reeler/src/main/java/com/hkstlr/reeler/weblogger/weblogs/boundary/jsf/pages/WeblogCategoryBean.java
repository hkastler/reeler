/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogCategoryBean {

    private WeblogCategory weblogCategory;

    public WeblogCategoryBean() {
    }

    public WeblogCategory getWeblogCategory() {
        return weblogCategory;
    }

    public void setWeblogCategory(WeblogCategory weblogCategory) {
        this.weblogCategory = weblogCategory;
    }

    
    
    public void action(WeblogCategory weblogCategory, String page) throws WebloggerException {
        this.weblogCategory = weblogCategory;
        
    }
    
    public void categoryViewAction() {

    }

}
