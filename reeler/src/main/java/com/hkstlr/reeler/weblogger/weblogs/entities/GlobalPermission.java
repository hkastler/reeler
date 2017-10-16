/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import java.io.Serializable;
import java.security.Permission;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.app.entities.PermissionEntity;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import com.hkstlr.reeler.weblogger.weblogs.control.StringChanger;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.DiscriminatorValue;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_permission")
@DiscriminatorValue("Global")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GlobalPermission.findAll", query = "SELECT r FROM GlobalPermission r")
    , @NamedQuery(name = "GlobalPermission.findById", query = "SELECT r FROM GlobalPermission r WHERE r.id = :id")
    , @NamedQuery(name = "GlobalPermission.findByUserName", query = "SELECT r FROM GlobalPermission r WHERE r.userName = :userName")
    , @NamedQuery(name = "GlobalPermission.findByActions", query = "SELECT r FROM GlobalPermission r WHERE r.actions = :actions")
    , @NamedQuery(name = "GlobalPermission.findByObjectId", query = "SELECT r FROM GlobalPermission r WHERE r.objectId = :objectId")
    , @NamedQuery(name = "GlobalPermission.findByObjectType", query = "SELECT r FROM GlobalPermission r WHERE r.objectType = :objectType")
    , @NamedQuery(name = "GlobalPermission.findByPending", query = "SELECT r FROM GlobalPermission r WHERE r.pending = :pending")
    , @NamedQuery(name = "GlobalPermission.findByDateCreated", query = "SELECT r FROM GlobalPermission r WHERE r.dateCreated = :datecreated")
    , @NamedQuery(name = "GlobalPermission.getByUserName", query = "SELECT p FROM GlobalPermission p WHERE p.userName = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "GlobalPermission.getByUserName&Pending", query = "SELECT p FROM GlobalPermission p WHERE p.userName = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "GlobalPermission.getByWeblogId", query = "SELECT p FROM GlobalPermission p WHERE p.objectId = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "GlobalPermission.getByWeblogId&Pending", query = "SELECT p FROM GlobalPermission p WHERE p.objectId = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "GlobalPermission.getByWeblogIdIncludingPending", query = "SELECT p FROM GlobalPermission p WHERE p.objectId = ?1")
    , @NamedQuery(name = "GlobalPermission.getByUserName&WeblogId", query = "SELECT p FROM GlobalPermission p WHERE p.userName = ?1 AND p.objectId = ?2 AND p.pending <> true")
    , @NamedQuery(name = "GlobalPermission.getByUserName&WeblogIdIncludingPending", query = "SELECT p FROM GlobalPermission p WHERE p.userName = ?1 AND p.objectId = ?2")})
public class GlobalPermission extends PermissionEntity implements Serializable {

    protected static final long serialVersionUID = 1L;
    
    
    /** Allowed to login and edit profile */
    public static final String LOGIN  = "login";
    
    /** Allowed to login and do weblogging */
    public static final String WEBLOG = "weblog";

    /** Allowed to login and do everything, including site-wide admin */
    public static final String ADMIN  = "admin";
    
    public GlobalPermission() {
        
    }

    public GlobalPermission(String name) {
        
    }

    public GlobalPermission(String userName, Date dateCreated) {
       
        super.dateCreated = dateCreated;
    }
    
     /** 
     * Create global permission for one specific user initialized with the 
     * actions specified by array.
     * @param user User of permission.
     * @param actions
     * @throws com.hkstlr.reeler.app.control.WebloggerException
     * 
     */
    public GlobalPermission(User user, List<String> actions) throws WebloggerException {
        super("GlobalPermission user: " + user.getUserName());
        setActionsAsList(actions);
    }
    
    /**
     * Create global permission for one specific user initialized with the 
     * actions that are implied by the user's roles.
     * @param user User of permission.
     * @throws com.hkstlr.reeler.app.control.WebloggerException
     */
    public GlobalPermission(User user) throws WebloggerException {
        super("GlobalPermission user: " + user.getUserName());
        
        // loop through user's roles, adding actions implied by each
        //List<String> roles = WebloggerFactory.getWeblogger().getUserManager().getRoles(user);
        Set<UserRole> userRoles = user.getRoles();        
        List<String> roles = new ArrayList<>();
        userRoles.forEach(userRole -> roles.add(userRole.getRoleName()));
        List<String> actionsList = new ArrayList<>();        
        for (String role : roles) {
            String impliedActions = WebloggerConfig.getProperty("role.action." + role);
            if (impliedActions != null) {
                List<String> toAdds = StringChanger.stringToStringList(impliedActions, ",");
                for (String toAdd : toAdds) {
                    if (!actionsList.contains(toAdd)) {
                        actionsList.add(toAdd);
                    }
                }
            }
        }
        setActionsAsList(actionsList);
    }
    
    /** 
     * Create global permission with the actions specified by array.
     * @param actions actions to add to permission
     * @throws org.apache.roller.weblogger.WebloggerException
     */
    public GlobalPermission(List<String> actions) throws WebloggerException {
        super("GlobalPermission user: N/A");
        setActionsAsList(actions);
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
        if (!(object instanceof GlobalPermission)) {
            return false;
        }
        GlobalPermission other = (GlobalPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.GlobalPermission[ id=" + id + " ]";
    }

    public boolean implies(PermissionEntity permission) {
        // TODO Auto-generated method stub
        return false;
    }

}
