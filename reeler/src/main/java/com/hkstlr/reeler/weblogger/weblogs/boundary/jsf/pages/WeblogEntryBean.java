/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;



/**
 *
 * @author henry.kastler
 */
@ManagedBean
public class WeblogEntryBean {

    @EJB
    Weblogger weblogger;
    
    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.anchor}")
    private String anchor;

    @Inject
    private Logger log;

    private WeblogEntry weblogEntry;

    private Weblog weblog;

    private List<WeblogEntryComment> comments;

    private WeblogEntryComment weblogEntryComment = new WeblogEntryComment();

    private boolean commentIsPosted = false;

    public WeblogEntryBean() {
    }

    @PostConstruct
    private void init() {
        try {
            this.weblogEntry = getEntryByHandleAndAnchor(handle, anchor);
            log.log(Level.INFO,"weblogEntry:" + weblogEntry.getAnchor());
            this.weblogEntryComment.setWeblogEntry(weblogEntry);
            this.weblog = weblogEntry.getWebsite();
            this.comments = getComments(weblogEntry);
        } catch (Exception e) {
            log.log(Level.SEVERE,"WeblogEntryBean init error:", e);            
        }

    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public WeblogEntry getWeblogEntry() {
        return weblogEntry;
    }

    public void setWeblogEntry(WeblogEntry weblogEntry) {
        this.weblogEntry = weblogEntry;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public List<WeblogEntryComment> getComments() {
        return comments;
    }

    public void setComments(List<WeblogEntryComment> comments) {
        this.comments = comments;
    }

    public WeblogEntryComment getWeblogEntryComment() {
        return weblogEntryComment;
    }

    public void setWeblogEntryComment(WeblogEntryComment weblogEntryComment) {
        this.weblogEntryComment = weblogEntryComment;
    }

    public boolean isCommentIsPosted() {
        return commentIsPosted;
    }

    public void setCommentIsPosted(boolean commentIsPosted) {
        this.commentIsPosted = commentIsPosted;
    }

    public WeblogEntry getEntryByHandleAndAnchor(String handle, String anchor) {
        log.info("handle:" + handle);
        log.info("anchor:" + anchor);
        return weblogger.getWeblogEntryManager().getWeblogEntryByHandleAndAnchor(handle, anchor);
    }

    public List<WeblogEntryComment> getComments(WeblogEntry entry) {
        List<WeblogEntryComment> _comments = null;

        _comments = weblogger.getWeblogEntryManager().getComments(entry);

        return _comments;
    }

    public void postComment() {
        log.info("postingComment");
        log.info("numberOfComments:" + getComments(this.weblogEntry).size());
        //this.weblogEntryComment.setWeblogEntry(this.weblogEntry);
        //call this to get around lazy load issues
        this.comments = this.weblogEntryComment.getWeblogEntry().getComments();
        weblogger.getWeblogEntryCommentManager().saveAndLoadComments(this.weblogEntryComment);
        this.commentIsPosted = true;
        log.log(Level.FINE, "comment posted, commentIsPosted: " + this.isCommentIsPosted());
        this.comments = getComments(this.weblogEntry);
        //log.log(Level.FINE, "numberOfComments:" + this.weblogEntry.getComments().size());
    }

}
