/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.logging.Logger;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryCommentVisitor {

    private static final Logger log = Logger.getLogger(WeblogEntryCommentVisitor.class.getName());

       
    private boolean spam;
    
    private boolean approved;

    private WeblogEntryComment weblogEntryComment;

    public WeblogEntryCommentVisitor() {
    }
    
    public WeblogEntryCommentVisitor(boolean isSpam, WeblogEntryComment weblogEntryComment) {
        this.spam = isSpam;
        this.approved = false;
        this.weblogEntryComment = weblogEntryComment;
    }

    public WeblogEntryCommentVisitor(boolean spam, boolean approved, WeblogEntryComment weblogEntryComment) {
        this.spam = spam;
        this.approved = approved;
        this.weblogEntryComment = weblogEntryComment;
    }
    
    /**
     * Get the value of weblogEntryComment
     *
     * @return the value of weblogEntryComment
     */
    public WeblogEntryComment getWeblogEntryComment() {
        return weblogEntryComment;
    }

    /**
     * Set the value of weblogEntryComment
     *
     * @param weblogEntryComment new value of weblogEntryComment
     */
    public void setWeblogEntryComment(WeblogEntryComment weblogEntryComment) {
        this.weblogEntryComment = weblogEntryComment;
    }

    /**
     * Get the value of spam
     *
     * @return the value of spam
     */
    public boolean isSpam() {
        return spam;
    }

    /**
     * Set the value of spam
     *
     * @param spam new value of spam
     */
    public void setSpam(boolean spam) {
        this.spam = spam;        
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    public void setSpamAndApproval(){
        log.info("spam:" + spam);
        log.info("approved:" + approved);
        if (this.spam && this.approved) {
            log.info("both spam and approved");
            if (!this.getWeblogEntryComment().getApproved()) {
                log.info("this comment was not approved before");
                this.getWeblogEntryComment().setApproved();
                this.spam = false;
                //msg.append("Approved");
            }else if (!this.getWeblogEntryComment().getSpam()) {
                log.info("this comment was not spam before");
                this.getWeblogEntryComment().setSpam();
                this.approved = false;
                //msg.append("Spam");
            }
        }else if (this.isSpam()) {
            log.info("isSpam, setting");
            this.getWeblogEntryComment().setSpam();
            this.approved = false;
            
        } else if (this.isApproved()) {
            log.info("isApproved, setting");
            this.getWeblogEntryComment().setApproved();
            this.spam = false;
            //msg.append("Approved");
        } else {
            //it is no longer approved
            log.info("is neither spam nor approved");
            this.getWeblogEntryComment().setStatus(WeblogEntryComment.ApprovalStatus.PENDING);
        }
        
    }
    
    

}
