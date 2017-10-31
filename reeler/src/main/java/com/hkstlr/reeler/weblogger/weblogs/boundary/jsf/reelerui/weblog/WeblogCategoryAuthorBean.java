/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import java.util.Objects;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogCategoryAuthorBean extends AuthorBean {
    
    
    private WeblogCategory weblogCategory;    
    
    private Logger log = Logger.getLogger(WeblogCategoryAuthorBean.class.getName());

    public WeblogCategoryAuthorBean() {
        super();
    }
    
    @PostConstruct
    private void init(){       
        
        setActionLabel();        
        
        if(this.id != null && !this.id.isEmpty()){
            this.weblogCategory = weblogger.getWeblogCategoryManager().findById(id);
        }else{            
            this.weblogCategory = new WeblogCategory(reelerUiBean.getCurrentWeblog());            
        }
        
        if(this.action == null || this.action.isEmpty()){
            this.action = "create";
            this.actionLabel = "Create";
        }
    }

    
    public WeblogCategory getWeblogCategory() {
        return weblogCategory;
    }

    public void setWeblogCategory(WeblogCategory weblogCategory) {
        this.weblogCategory = weblogCategory;
    }

    public void save() {       
        
        weblogger.getWeblogCategoryManager().save(weblogCategory);
        weblogCategory = weblogger.getWeblogCategoryManager().find(weblogCategory.getId());
        //refresh the view
        reelerUiBean.setCurrentWeblog(weblogger.getWeblogManager().find(weblogCategory.getWeblog().getId())); 
        FacesMessageManager.addSuccessMessage("weblogCategoryForm", "Category Saved");
    }
       
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.weblogCategory);
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
       
        return true;
    }

    
    

   

    
}
