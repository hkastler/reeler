/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogBookmarkAuthorBean extends AuthorBean {
    
    @Inject
    private Logger log;
    
    private WeblogBookmark weblogBookmark;

    public WeblogBookmarkAuthorBean() {
    }

    public WeblogBookmarkAuthorBean(Class entityClass) {
        super(entityClass);
    }
    
    @PostConstruct
    private void init(){
        //log.info("getting categories for Weblog:" + weblog.getName());
        //this.weblogCategories = weblogger.getWeblogCategoryManager().getWeblogCategoriesForWeblog(weblog);
        if(this.id != null && !this.id.isEmpty()){
            this.weblogBookmark = weblogger.getWeblogBookmarkManager().findById(id);
        }else{
            log.info("initing new WeblogCatgory for " + reelerUiBean.getCurrentWeblog().getName());
            
            this.weblogBookmark = new WeblogBookmark();
            //this.weblogBookmark.setFolderid(folder);
        }
        
        if(this.action == null || this.action.isEmpty()){
            this.action = "create";
            this.actionLabel = "Create";
        }
    }

    public WeblogBookmark getWeblogBookmark() {
        return weblogBookmark;
    }

    public void setWeblogBookmark(WeblogBookmark weblogBookmark) {
        this.weblogBookmark = weblogBookmark;
    }
    
   
    public void save() {
        weblogger.getWeblogBookmarkManager().save(weblogBookmark);
        FacesMessageManager.addSuccessMessage("weblogBookmarkForm", "Bookmark saved");
    }
    
}
