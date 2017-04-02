/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
public class WeblogEntryCommentBean {
    
    @EJB
    Weblogger weblogger;
    
    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;
    
    private List<Object[]> commentObject;
    
    private List<WeblogEntryComment> weblogEntryComments = new ArrayList<>();
    
    @Inject
    private Logger log;

    public WeblogEntryCommentBean() {
    }
    
    @PostConstruct
    private void init(){
        log.info("getting comments for Weblog:" + weblog.getName());
        this.weblogEntryComments = weblogger.getWeblogEntryCommentManager()
                .getCommentsForWeblog(weblog, WeblogEntryComment.ApprovalStatus.APPROVED);
        
        
    }

    public List<Object[]> getCommentObject() {
        return commentObject;
    }

    public void setCommentObject(List<Object[]> commentObject) {
        this.commentObject = commentObject;
    }

    public List<WeblogEntryComment> getWeblogEntryComments() {
        return weblogEntryComments;
    }

    public void setWeblogEntryComments(List<WeblogEntryComment> weblogEntryComments) {
        this.weblogEntryComments = weblogEntryComments;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }
    
    public void updateComment(WeblogEntryComment updatedComment){
        weblogger.getWeblogEntryCommentManager().save(updatedComment);
        FacesMessageManager.addSuccessMessage("commentForm", "Comment updated");
    }
    
    public void setSpam(WeblogEntryComment updatedComment){
        log.log(Level.INFO,"updating comment spam");
        updatedComment.setSpam();
        weblogger.getWeblogEntryCommentManager().save(updatedComment);
        FacesMessageManager.addSuccessMessage("commentForm", "Comment updated");
    }
    
    
    
}
