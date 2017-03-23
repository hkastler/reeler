/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.entities.WeblogEntryComment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogCategoryBean {
    
    @EJB
    Weblogger weblogger;
    
    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;
    
    private List<WeblogCategory> weblogCategories = new ArrayList<>();
    
    @Inject
    private Logger log;

    public WeblogCategoryBean() {
    }
    
    @PostConstruct
    private void init(){
        log.info("getting categories for Weblog:" + weblog.getName());
        this.weblogCategories = weblogger.getWeblogCategoryManager().getWeblogCategoriesForWeblog(weblog);
        
    }

    
    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public List<WeblogCategory> getWeblogCategories() {
        return weblogCategories;
    }

    public void setWeblogCategories(List<WeblogCategory> weblogCategories) {
        this.weblogCategories = weblogCategories;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.weblogCategories);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WeblogCategoryBean other = (WeblogCategoryBean) obj;
        return true;
    }

   

    
}
