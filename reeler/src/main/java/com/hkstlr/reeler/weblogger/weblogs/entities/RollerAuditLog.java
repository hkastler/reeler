/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "roller_audit_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RollerAuditLog.findAll", query = "SELECT r FROM RollerAuditLog r")
    , @NamedQuery(name = "RollerAuditLog.findById", query = "SELECT r FROM RollerAuditLog r WHERE r.id = :id")
    , @NamedQuery(name = "RollerAuditLog.findByUserId", query = "SELECT r FROM RollerAuditLog r WHERE r.userId = :userId")
    , @NamedQuery(name = "RollerAuditLog.findByObjectId", query = "SELECT r FROM RollerAuditLog r WHERE r.objectId = :objectId")
    , @NamedQuery(name = "RollerAuditLog.findByObjectClass", query = "SELECT r FROM RollerAuditLog r WHERE r.objectClass = :objectClass")
    , @NamedQuery(name = "RollerAuditLog.findByCommentText", query = "SELECT r FROM RollerAuditLog r WHERE r.commentText = :commentText")
    , @NamedQuery(name = "RollerAuditLog.findByChangeTime", query = "SELECT r FROM RollerAuditLog r WHERE r.changeTime = :changeTime")})
public class RollerAuditLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "user_id", nullable = false, length = 48)
    private String userId;
    @Size(max = 48)
    @Column(name = "object_id", length = 48)
    private String objectId;
    @Size(max = 255)
    @Column(name = "object_class", length = 255)
    private String objectClass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "comment_text", nullable = false, length = 255)
    private String commentText;
    @Column(name = "change_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;

    public RollerAuditLog() {
    }

    public RollerAuditLog(String id) {
        this.id = id;
    }

    public RollerAuditLog(String id, String userId, String commentText) {
        this.id = id;
        this.userId = userId;
        this.commentText = commentText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
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
        if (!(object instanceof RollerAuditLog)) {
            return false;
        }
        RollerAuditLog other = (RollerAuditLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.RollerAuditLog[ id=" + id + " ]";
    }
    
}
