/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.PageBean;
import com.hkstlr.reeler.app.control.Paginator;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryCommentVisitor;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.ArrayList;
import java.util.List;
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
public class WeblogEntryCommentBean extends PageBean {

    @EJB
    private transient Weblogger weblogger;

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;
    
    @ManagedProperty(value = "#{param.entryid}")
    private String entryid;

    private List<WeblogEntryCommentVisitor> weblogEntryCommentVisitors = new ArrayList<>();

    @Inject
    private Logger log;

    public WeblogEntryCommentBean() {
    }

    @PostConstruct
    public void init() {
        
        List<WeblogEntryComment.ApprovalStatus> statuses = new ArrayList<>();
        statuses.add(WeblogEntryComment.ApprovalStatus.SPAM);
        statuses.add(WeblogEntryComment.ApprovalStatus.APPROVED);
        statuses.add(WeblogEntryComment.ApprovalStatus.PENDING);
        List<WeblogEntryComment> weblogEntryComments;
        if(entryid == null){
            weblogEntryComments = weblogger.getWeblogEntryCommentManager()
                .getCommentsForWeblog(weblog,statuses,null);
        }else{
            WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().findById(entryid);
            weblogEntryComments = weblogger.getWeblogEntryCommentManager()
                .getComments(weblogEntry);
        }
        
        
        if(pageSize == null){
            pageSize = weblog.getEntryDisplayCount();
        }        
        paginator = new Paginator(weblogEntryComments.size(),
                                  this.getPageNum(),
                                  weblogEntryComments.size());
        
        for (WeblogEntryComment wec : weblogEntryComments) {
            WeblogEntryCommentVisitor wecv = new WeblogEntryCommentVisitor(wec.getSpam(), wec.getApproved(), wec);
            weblogEntryCommentVisitors.add(wecv);
        }
    }

    public List<WeblogEntryCommentVisitor> getWeblogEntryCommentVisitors() {
        return weblogEntryCommentVisitors;
    }

    public void setWeblogEntryCommentVisitors(List<WeblogEntryCommentVisitor> weblogEntryCommentVisitors) {
        this.weblogEntryCommentVisitors = weblogEntryCommentVisitors;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }
    
    

    public void updateComment(WeblogEntryComment updatedComment) {
        weblogger.getWeblogEntryCommentManager().save(updatedComment);
        FacesMessageManager.addSuccessMessage("commentForm", "Comment updated");
    }

    public void setSpamAndApproval(WeblogEntryCommentVisitor visitedComment) {

        visitedComment.setSpamAndApproval();
        log.info("saving comment:" + visitedComment.getWeblogEntryComment().getContent());
        log.info("saving comment:" + visitedComment.getWeblogEntryComment().getApproved());
        weblogger.getWeblogEntryCommentManager().save(visitedComment.getWeblogEntryComment());
        weblogger.getWeblogEntryCommentManager().getComments(
                visitedComment.getWeblogEntryComment().getWeblogEntry());
        if(visitedComment.getWeblogEntryComment().getSpam()){
           FacesMessageManager.addErrorMessage("Spam");           
        }
        if(visitedComment.getWeblogEntryComment().getApproved()){
           FacesMessageManager.addSuccessMessage("isApprovedForm", "Approved");
        }
       
        
    }
    
    public void commentsViewAction(){
       //needs to be in init for ajax to work
    }

}
