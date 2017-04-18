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
    Weblogger weblogger;

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;

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
        List<WeblogEntryComment> weblogEntryComments = weblogger.getWeblogEntryCommentManager()
                .getCommentsForWeblog(weblog,statuses,null);
        if(pageSize == null){
            pageSize = weblog.getEntryDisplayCount();
        }        
        paginator = new Paginator(weblog.getEntryDisplayCount(),
                                  this.getPageNum(),
                                  weblogEntryComments.size());
        int[] range = new int[2];
        range[0] = getPaginator().getPageFirstItem()-1;
        range[1] = getPaginator().getPageLastItem()-1;
        weblogEntryComments = weblogger.getWeblogEntryCommentManager()
                .getCommentsForWeblog(weblog,statuses,range);
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

    public void updateComment(WeblogEntryComment updatedComment) {
        weblogger.getWeblogEntryCommentManager().save(updatedComment);
        FacesMessageManager.addSuccessMessage("commentForm", "Comment updated");
    }

    public void setSpamAndApproval(WeblogEntryCommentVisitor visitedComment) {

        visitedComment.setSpamAndApproval();
        if(visitedComment.getWeblogEntryComment().getSpam()){
           FacesMessageManager.addErrorMessage("Spam");           
        }
        if(visitedComment.getWeblogEntryComment().getApproved()){
           FacesMessageManager.addSuccessMessage("isApprovedForm", "Approved");
        }
        weblogger.getWeblogEntryCommentManager().save(visitedComment.getWeblogEntryComment());
        
    }
    
    public void commentsViewAction(){
       //needs to be in init for ajax to work
    }

}
