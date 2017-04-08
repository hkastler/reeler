/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.io.Serializable;
import java.security.Permission;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.app.entities.AbstractPermissionEntity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import static javax.persistence.DiscriminatorType.STRING;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import javax.validation.constraints.Size;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_permission")
@DiscriminatorValue("Global")
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
    , @NamedQuery(name = "ObjectPermission.getByUserName", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "ObjectPermission.getByUserName&Pending", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "ObjectPermission.getByWeblogId", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "ObjectPermission.getByWeblogId&Pending", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "ObjectPermission.getByWeblogIdIncludingPending", query = "SELECT p FROM ObjectPermission p WHERE p.objectId = ?1")
    , @NamedQuery(name = "ObjectPermission.getByUserName&WeblogId", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.objectId = ?2 AND p.pending <> true")
    , @NamedQuery(name = "ObjectPermission.getByUserName&WeblogIdIncludingPending", query = "SELECT p FROM ObjectPermission p WHERE p.userName = ?1 AND p.objectId = ?2")})
public class ObjectPermission extends AbstractPermissionEntity implements Serializable {

    protected static final long serialVersionUID = 1L;
    
    public ObjectPermission() {
        super("");
    }

    public ObjectPermission(String name) {
        super(name);
    }

    public ObjectPermission(String userName, Date dateCreated) {
        super(userName);
        super.dateCreated = dateCreated;
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

}
