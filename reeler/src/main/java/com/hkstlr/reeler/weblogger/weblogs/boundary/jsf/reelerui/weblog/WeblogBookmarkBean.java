/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogBookmarkBean {

    @EJB
    Weblogger weblogger;

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;

    public List<WeblogBookmark> bookmarks = new ArrayList<>();

    public WeblogBookmarkBean() {
    }
    
    @PostConstruct
    public void init(){
        this.bookmarks = weblogger.getWeblogBookmarkManager().getBookmarksForWeblog(weblog);
        
    }

    public List<WeblogBookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<WeblogBookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }
    
    

}
