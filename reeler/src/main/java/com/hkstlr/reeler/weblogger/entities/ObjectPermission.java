/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.entities;

import java.io.Serializable;
import java.security.Permission;
import java.util.Date;
import java.util.List;

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

import com.hkstlr.reeler.app.control.AppPermission;;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjectPermission.findAll", query = "SELECT r FROM ObjectPermission r")
    , @NamedQuery(name = "ObjectPermission.findById", query = "SELECT r FROM ObjectPermission r WHERE r.id = :id")
    , @NamedQuery(name = "ObjectPermission.findByUserName", query = "SELECT r FROM ObjectPermission r WHERE r.userName = :userName")
    , @NamedQuery(name = "ObjectPermission.findByActions", query = "SELECT r FROM ObjectPermission r WHERE r.actions = :actions")
    , @NamedQuery(name = "ObjectPermission.findByObjectId", query = "SELECT r FROM ObjectPermission r WHERE r.objectId = :objectId")
    , @NamedQuery(name = "ObjectPermission.findByObjectType", query = "SELECT r FROM ObjectPermission r WHERE r.objectType = :objectType")
    , @NamedQuery(name = "ObjectPermission.findByPending", query = "SELECT r FROM ObjectPermission r WHERE r.pending = :pending")
    , @NamedQuery(name = "ObjectPermission.findByDateCreated", query = "SELECT r FROM ObjectPermission r WHERE r.dateCreated = :datecreated")
    , @NamedQuery(name = "WeblogPermission.getByUserName", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.pending != TRUE")
    , @NamedQuery(name = "WeblogPermission.getByUserName&Pending", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogId", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogId&Pending", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogIdIncludingPending", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1")
    , @NamedQuery(name = "WeblogPermission.getByUserName&WeblogId", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.objectId = ?2 AND p.pending != true")
    , @NamedQuery(name = "WeblogPermission.getByUserName&WeblogIdIncludingPending", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.objectId = ?2")})
public class ObjectPermission extends AppPermission implements Serializable {

    protected static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    protected String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username", nullable = false, length = 255)
    protected String userName;
    
    @Size(max = 255)
    @Column(name = "actions", length = 255)
    protected String actions;
    
    @Size(max = 48)
    @Column(name = "objectid", length = 48)
    protected String objectId;
    
    @Size(max = 255)
    @Column(name = "objecttype", length = 255)
    protected String objectType;
    
    @Column(name = "pending")    
    protected Boolean pending;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreated;

    public ObjectPermission() {
    	super("");
    }

    public ObjectPermission(String id) {
        super(id);
    }

    public ObjectPermission(String id, String userName, Date datecreated) {
    	super(id);
        this.userName = userName;
        this.dateCreated = datecreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public Date getDatecreated() {
        return dateCreated;
    }

    public void setDatecreated(Date datecreated) {
        this.dateCreated = datecreated;
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
        if (!(object instanceof ObjectPermission)) {
            return false;
        }
        ObjectPermission other = (ObjectPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.ObjectPermission[ id=" + id + " ]";
    }

	@Override
	public boolean implies(Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getActionsAsList() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
