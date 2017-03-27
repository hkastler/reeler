/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogEntryComment.findAll", query = "SELECT r FROM WeblogEntryComment r")
    , @NamedQuery(name = "WeblogEntryComment.findById", query = "SELECT r FROM WeblogEntryComment r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogEntryComment.findByName", query = "SELECT r FROM WeblogEntryComment r WHERE r.name = :name")
    , @NamedQuery(name = "WeblogEntryComment.findByEmail", query = "SELECT r FROM WeblogEntryComment r WHERE r.email = :email")
    , @NamedQuery(name = "WeblogEntryComment.findByUrl", query = "SELECT r FROM WeblogEntryComment r WHERE r.url = :url")
    , @NamedQuery(name = "WeblogEntryComment.findByContent", query = "SELECT r FROM WeblogEntryComment r WHERE r.content = :content")
    , @NamedQuery(name = "WeblogEntryComment.findByPostTime", query = "SELECT r FROM WeblogEntryComment r WHERE r.postTime = :posttime")
    , @NamedQuery(name = "WeblogEntryComment.findByNotify", query = "SELECT r FROM WeblogEntryComment r WHERE r.notify = :notify")
    , @NamedQuery(name = "WeblogEntryComment.findByRemoteHost", query = "SELECT r FROM WeblogEntryComment r WHERE r.remoteHost = :remotehost")
    , @NamedQuery(name = "WeblogEntryComment.findByReferrer", query = "SELECT r FROM WeblogEntryComment r WHERE r.referrer = :referrer")
    , @NamedQuery(name = "WeblogEntryComment.findByUserAgent", query = "SELECT r FROM WeblogEntryComment r WHERE r.userAgent = :useragent")
    , @NamedQuery(name = "WeblogEntryComment.findByStatus", query = "SELECT r FROM WeblogEntryComment r WHERE r.status = :status")
    , @NamedQuery(name = "WeblogEntryComment.findByPlugins", query = "SELECT r FROM WeblogEntryComment r WHERE r.plugins = :plugins")
    , @NamedQuery(name = "WeblogEntryComment.findByContentType", query = "SELECT r FROM WeblogEntryComment r WHERE r.contentType = :contentType")
    , @NamedQuery(name = "WeblogEntryComment.findByWeblogEntry", query = "SELECT r FROM WeblogEntryComment r WHERE r.weblogEntry = :weblogEntry")
    , @NamedQuery(name = "WeblogEntryComment.getCountAllDistinctByStatus", query = "SELECT COUNT(c) FROM WeblogEntryComment c where c.status = ?1")
    , @NamedQuery(name = "WeblogEntryComment.getCountDistinctByWebsite&amp;Status", query = "SELECT COUNT(c) FROM WeblogEntryComment c WHERE c.weblogEntry.website = ?1 AND c.status = ?2")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWebsiteByEndDate", query = "SELECT COUNT(c), c.weblogEntry.website.id, c.weblogEntry.website.handle, c.weblogEntry.website.name FROM WeblogEntryComment c WHERE c.weblogEntry.pubTime < ?1 GROUP BY c.weblogEntry.website.id, c.weblogEntry.website.handle, c.weblogEntry.website.name")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWebsiteByEndDate&amp;StartDate", query = "SELECT COUNT(c), c.weblogEntry.website.id, c.weblogEntry.website.handle, c.weblogEntry.website.name FROM WeblogEntryComment c WHERE c.weblogEntry.pubTime < ?1 AND c.weblogEntry.pubTime > ?2 GROUP BY c.weblogEntry.website.id, c.weblogEntry.website.handle, c.weblogEntry.website.name")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWeblogEntryByEndDate", query = "SELECT COUNT(c), c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title FROM WeblogEntryComment c WHERE c.weblogEntry.pubTime < ?1 GROUP BY c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWeblogEntryByEndDate&amp;StartDate", query = "SELECT COUNT(c), c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title FROM WeblogEntryComment c WHERE c.weblogEntry.pubTime < ?1 AND c.weblogEntry.pubTime > ?2 GROUP BY c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWeblogEntryByWebsite&amp;EndDate", query = "SELECT COUNT(c), c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title FROM WeblogEntryComment c WHERE c.weblogEntry.website = ?1 AND c.weblogEntry.pubTime < ?2 GROUP BY c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title")
    , @NamedQuery(name = "WeblogEntryComment.getMostCommentedWeblogEntryByWebsite&amp;EndDate&amp;StartDate", query = "SELECT COUNT(c), c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title FROM WeblogEntryComment c WHERE c.weblogEntry.website = ?1 AND c.weblogEntry.pubTime < ?2 AND c.weblogEntry.pubTime > ?3 GROUP BY c.weblogEntry.website.handle, c.weblogEntry.anchor, c.weblogEntry.title")})
public class WeblogEntryComment extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // approval status states
    public enum ApprovalStatus {
        APPROVED, DISAPPROVED, SPAM, PENDING
    }

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "url", length = 255)
    private String url;

    @Size(max = 2147483647)
    @Column(name = "content", length = 2147483647)
    private String content;

    @Basic(optional = false)
    @NotNull
    @Column(name = "posttime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "notify", nullable = false)
    private boolean notify;

    @Size(max = 128)
    @Column(name = "remotehost", length = 128)
    private String remoteHost;

    @Size(max = 255)
    @Column(name = "referrer", length = 255)
    private String referrer;

    @Size(max = 255)
    @Column(name = "useragent", length = 255)
    private String userAgent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Size(max = 255)
    @Column(name = "plugins", length = 255)
    private String plugins;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "contenttype", nullable = false, length = 128)
    private String contentType;

    @JoinColumn(name = "entryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeblogEntry weblogEntry;

    public WeblogEntryComment() {
    }

    public WeblogEntryComment(String id, boolean notify, ApprovalStatus status, String contenttype) {

        this.notify = notify;
        this.status = status;
        this.contentType = contenttype;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public WeblogEntry getWeblogEntry() {
        return weblogEntry;
    }

    public void setWeblogEntry(WeblogEntry weblogEntry) {
        this.weblogEntry = weblogEntry;
    }

    /**
     * Indicates that weblog owner considers this comment to be spam.
     */
    public Boolean getSpam() {
        return ApprovalStatus.SPAM.equals(getStatus());
    }

    /**
     * True if comment has is pending moderator approval.
     */
    public Boolean getPending() {
        return ApprovalStatus.PENDING.equals(getStatus());
    }

    /**
     * Indicates that comment has been approved for display on weblog.
     */
    public Boolean getApproved() {
        return ApprovalStatus.APPROVED.equals(getStatus());
    }

    /**
     * Timestamp to be used to formulate comment permlink.
     */
    public String getTimestamp() {
        if (getPostTime() != null) {
            return Long.toString(getPostTime().getTime());
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeblogEntryComment)) {
            return false;
        }
        WeblogEntryComment other = (WeblogEntryComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //------------------------------------------------------- Good citizenship
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append(getId());
        buf.append(", ").append(getName());
        buf.append(", ").append(getEmail());
        buf.append(", ").append(getPostTime());
        buf.append("}");
        return buf.toString();
    }

    @PrePersist
    private void setApproval() {
        //TODO: figure out why the timestamp doesnt set the date in the db; postgresql at least
        this.postTime = new Date();
        this.notify = false;
        this.status = ApprovalStatus.APPROVED;
        this.contentType = "text/plain";
    }

}
