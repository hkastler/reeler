/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.app.entities;

import com.hkstlr.reeler.app.control.AppPermissionInterface;
import com.hkstlr.reeler.weblogger.weblogs.control.StringChanger;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import java.security.Permission;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import static javax.persistence.DiscriminatorType.STRING;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henry.kastler
 */

@Inheritance(strategy=SINGLE_TABLE)
@DiscriminatorColumn(name = "objecttype")
@DiscriminatorValue(value = "objecttype")
@MappedSuperclass
public class AbstractPermissionEntity extends java.security.Permission implements AppPermissionInterface {

    protected static final long serialVersionUID = 1L;
    
    @Transient
    @Inject
    private Logger log;
    /**
     *
     */
    @Id
    @Basic(optional = false)
    @NotNull(message = "{id.NotNull}")
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    protected final String id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username", nullable = false, length = 255)
    protected String userName;

    @Size(max = 48)
    @Column(name = "objectid", length = 48)
    protected String objectId;

    
    @Size(max = 255)
    @Column(name = "objecttype", length = 255, insertable = true, updatable = false )
    protected String objectType;

    @Column(name = "pending")
    protected Boolean pending;

    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreated = new Date();    
    
    @Size(max = 255)
    @Column(name = "actions", length = 255)
    protected String actions;
    
    /** Allowed to login and edit profile */
    public static final String LOGIN  = "login";
    
    /** Allowed to login and do weblogging */
    public static final String WEBLOG = "weblog";

    /** Allowed to login and do everything, including site-wide admin */
    public static final String ADMIN  = "admin";

    public AbstractPermissionEntity(String name) {
        super(name);
        this.id = UUID.randomUUID().toString();
    }
    
    
    public String getActions() {
        return actions;
    }

    
    public void setActions(String actions) {
        this.actions = actions;
    }

    
    public void addActions(WeblogPermission perm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void addActions(List<String> newActions) {
        List<String> updatedActions = getActionsAsList();
        for (String newAction : newActions) {
            if (!updatedActions.contains(newAction)) {
                updatedActions.add(newAction);
            }
        }
        setActionsAsList(updatedActions);
    }

    public List<String> getActionsAsList() {
        return StringChanger.stringToStringList(getActions(), ",");
    }
    
    public void setActionsAsList(List<String> actionsList) {
        setActions(StringChanger.stringListToString(actionsList, ","));
    }

    public boolean hasAction(String action) {
         List<String> actionList = getActionsAsList();
        return actionList.contains(action);
    }

    public boolean hasActions(List<String> actionsToCheck) {
        List<String> actionList = getActionsAsList();
        for (String actionToCheck : actionsToCheck) {
            if (!actionList.contains(actionToCheck)) {
                return false;
            }
        }
        return true;
    }

     /**
     * True if permission specifies no actions
     * @return 
     */
    public boolean isEmpty() {
        return (getActions() == null || getActions().trim().length() == 0);
    }

    public void removeActions(List<String> actionsToRemove) {
       List<String> updatedActions = getActionsAsList();
        for (String actionToRemove : actionsToRemove) {
            updatedActions.remove(actionToRemove);
        }
        log.fine("removeActions: " + updatedActions);
        setActionsAsList(updatedActions);
    }
    
    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(object instanceof AbstractPermissionEntity)) {
            return false;
        }
        AbstractPermissionEntity other = (AbstractPermissionEntity) object;
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