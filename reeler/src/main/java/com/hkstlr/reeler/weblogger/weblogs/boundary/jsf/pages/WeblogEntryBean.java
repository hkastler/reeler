/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
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
    private Logger LOG;

    private WeblogEntry weblogEntry;

    private Weblog weblog;

    private Collection<WeblogEntryComment> comments;

    private WeblogEntryComment weblogEntryComment = new WeblogEntryComment();

    private boolean commentIsPosted = false;
    
    private boolean showComments = false;
    
    private boolean showCommentForm = false;
    
    private Calendar todayCal;
    
    private Calendar allowCommentsCal;

    public WeblogEntryBean() {
        //constructor
    }

    @PostConstruct
    protected void init() {
        try {
            this.weblogEntry = getEntryByHandleAndAnchor(handle, anchor);
            
            this.weblogEntryComment.setWeblogEntry(weblogEntry);
            this.weblog = weblogEntry.getWebsite();
            this.comments = getComments(weblogEntry);
            this.showComments = weblogEntry.isAllowComments();
            LOG.info("website:" + weblog.toJsonString());
            Date now = new Date();
            todayCal = Calendar.getInstance(weblog.getLocaleInstance());
            todayCal.setTime(now);
            
            allowCommentsCal = this.weblogEntry.getPubTime();
            allowCommentsCal.add(Calendar.DAY_OF_YEAR,-weblogEntry.getCommentDays());
            
            long daysBetween = ChronoUnit.DAYS.between(allowCommentsCal.toInstant(), todayCal.toInstant());
            
            
            this.showCommentForm = this.showComments && (weblogEntry.getCommentDays() == 0 
                                                            || daysBetween < weblogEntry.getCommentDays());
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"WeblogEntryBean init error:", e);            
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

    public Collection<WeblogEntryComment> getComments() {
        return comments;
    }

    public void setComments(Collection<WeblogEntryComment> comments) {
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
        WeblogEntry getMe = weblogger.getWeblogEntryManager()
                .getWeblogEntryByHandleAndAnchor(handle, anchor);
        
        
        
        return getMe;
    }

    public List<WeblogEntryComment> getComments(WeblogEntry entry) {
        List<WeblogEntryComment> _comments = new LinkedList<>();

        List<WeblogEntryComment> comments = weblogger.getWeblogEntryCommentManager()
                .getCommentsByWeblogEntryAndStatus(entry, WeblogEntryComment.ApprovalStatus.APPROVED);
        comments.stream().map((comment) -> 
                weblogger.getPluginManager().applyCommentPlugins(comment,comment.getContent()))
                .forEachOrdered((comment) -> {
                    _comments.add(comment);
                });

        return _comments;
    }

    public void postComment() {
        LOG.fine("postingComment");
        LOG.fine("numberOfComments:" + getComments(this.weblogEntry).size());
        
        //ApprovalStatus.PENDING is the default
        boolean moderated = weblog.isModerateComments();
        LOG.info("moderateComments:" + moderated);
        if(!moderated){
             this.weblogEntryComment.setStatus(WeblogEntryComment.ApprovalStatus.APPROVED);
        }
        weblogger.getPluginManager().applyCommentPlugins(weblogEntryComment, weblogEntryComment.getContent());
        weblogger.getWeblogEntryCommentManager().saveAndLoadComments(this.weblogEntryComment);
        
        this.commentIsPosted = true;
        
        this.comments = getComments(this.weblogEntry);
        
        if(!moderated){
            FacesMessageManager.addSuccessMessage("commentMessage", "Comment Posted");
        }else{
            FacesMessageManager.addSuccessMessage("commentMessage", "Comments are moderated");
        }
    }

}
