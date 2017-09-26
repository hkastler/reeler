/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmarkFolder;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
public class WeblogBookmarkAuthorBean extends AuthorBean {
    
    @Inject
    private Logger log;
    
    private WeblogBookmark weblogBookmark;
    
    @ManagedProperty(value = "#{param.folderid}")
    private String folderid;

    public WeblogBookmarkAuthorBean() {
        //default constructor
    }

    public WeblogBookmarkAuthorBean(Class entityClass) {
        super(entityClass);
    }
    
    @PostConstruct
    protected void init(){
        
        setActionLabel();
        if(this.id != null && !this.id.isEmpty()){
            this.weblogBookmark = weblogger.getWeblogBookmarkManager().findById(id);
        }else{
            log.info("initing new WeblogCatgory for " + reelerUiBean.getCurrentWeblog().getName());
            
            this.weblogBookmark = new WeblogBookmark();
            WeblogBookmarkFolder wbf = weblogger.getWeblogBookmarkManager()
                    .getFolderById(folderid);
            this.weblogBookmark.setFolder(wbf);
        }
        
    }

    public WeblogBookmark getWeblogBookmark() {
        return weblogBookmark;
    }

    public void setWeblogBookmark(WeblogBookmark weblogBookmark) {
        this.weblogBookmark = weblogBookmark;
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid;
    }
    
    
    public void save() {
        weblogger.getWeblogBookmarkManager().edit(weblogBookmark);
        FacesMessageManager.addSuccessMessage("weblogBookmarkForm", "Bookmark saved");
    }
    
}
