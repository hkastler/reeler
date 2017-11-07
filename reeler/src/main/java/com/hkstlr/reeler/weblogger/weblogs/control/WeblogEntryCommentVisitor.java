/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.logging.Logger;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryCommentVisitor {

    private static final Logger LOG = Logger.getLogger(WeblogEntryCommentVisitor.class.getName());

       
    private boolean spam;
    
    private boolean approved;

    private WeblogEntryComment weblogEntryComment;

    public WeblogEntryCommentVisitor() {
        //constructor
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
        LOG.finest("spam:" + spam);
        LOG.finest("approved:" + approved);
        if (this.spam && this.approved) {
            LOG.finest("both spam and approved");
            if (!this.getWeblogEntryComment().getApproved()) {
                LOG.finest("this comment was not approved before");
                this.getWeblogEntryComment().setApproved();
                this.spam = false;
                
            }else if (!this.getWeblogEntryComment().getSpam()) {
                LOG.finest("this comment was not spam before");
                this.getWeblogEntryComment().setSpam();
                this.approved = false;
                
            }
        }else if (this.isSpam()) {
            LOG.finest("isSpam, setting");
            this.getWeblogEntryComment().setSpam();
            this.approved = false;
            
        } else if (this.isApproved()) {
            LOG.finest("isApproved, setting");
            this.getWeblogEntryComment().setApproved();
            this.spam = false;
        } else {
            //it is no longer approved
            LOG.finest("is neither spam nor approved");
            this.getWeblogEntryComment().setStatus(WeblogEntryComment.ApprovalStatus.PENDING);
        }
        
    }
    
    

}
