package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.entities.PermissionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.hkstlr.reeler.weblogger.users.entities.User;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "roller_permission")
@DiscriminatorValue(value = "Weblog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogPermission.findAll", query = "SELECT r FROM WeblogPermission r")
    , @NamedQuery(name = "WeblogPermission.findById", query = "SELECT r FROM WeblogPermission r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogPermission.findByUserName", query = "SELECT r FROM WeblogPermission r WHERE r.userName = :userName")
    , @NamedQuery(name = "WeblogPermission.findByActions", query = "SELECT r FROM WeblogPermission r WHERE r.actions = :actions")
    , @NamedQuery(name = "WeblogPermission.findByObjectId", query = "SELECT r FROM WeblogPermission r WHERE r.objectId = :objectId")
    , @NamedQuery(name = "WeblogPermission.findByObjectType", query = "SELECT r FROM WeblogPermission r WHERE r.objectType = :objectType")
    , @NamedQuery(name = "WeblogPermission.findByPending", query = "SELECT r FROM WeblogPermission r WHERE r.pending = :pending")
    , @NamedQuery(name = "WeblogPermission.findByDateCreated", query = "SELECT r FROM WeblogPermission r WHERE r.dateCreated = :datecreated")
    , @NamedQuery(name = "WeblogPermission.getByUserName", query = "SELECT p FROM WeblogPermission p WHERE p.userName = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "WeblogPermission.getByUserName&Pending", query = "SELECT p FROM WeblogPermission p WHERE p.userName = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogId", query = "SELECT p FROM WeblogPermission p WHERE p.objectId = ?1 AND p.pending <> TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogId&Pending", query = "SELECT p FROM WeblogPermission p WHERE p.objectId = ?1 AND p.pending = TRUE")
    , @NamedQuery(name = "WeblogPermission.getByWeblogIdIncludingPending", query = "SELECT p FROM WeblogPermission p WHERE p.objectId = ?1")
    , @NamedQuery(name = "WeblogPermission.getByUserName&WeblogId", query = "SELECT p FROM WeblogPermission p WHERE p.userName = ?1 AND p.objectId = ?2 AND p.pending <> true")
    , @NamedQuery(name = "WeblogPermission.getByUserName&WeblogIdIncludingPending", query = "SELECT p FROM WeblogPermission p WHERE p.userName = ?1 AND p.objectId = ?2")})

public class WeblogPermission extends PermissionEntity implements Serializable {

    public static final String EDIT_DRAFT = "edit_draft";
    public static final String POST = "post";
    public static final String ADMIN = "admin";
    protected static final List<String> ALL_ACTIONS = new ArrayList<String>();
    public static final String USER_WEBLOG_PERMISSION_NAME_PREFIX 
            = "WeblogPermission user: ";
    public static final String PERMISSION_OBJECT_TYPE =
            com.hkstlr.reeler.weblogger.weblogs.entities.Weblog.class.getSimpleName();

    protected static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull(message = "{id.NotNull}")
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    @Id
    protected final String id;
    
    static {
        ALL_ACTIONS.add(EDIT_DRAFT);
        ALL_ACTIONS.add(POST);
        ALL_ACTIONS.add(ADMIN);
    }

    public WeblogPermission() {
        super();
        this.id = UUID.randomUUID().toString();
    }
    
    public WeblogPermission(String name) {
        super(name);
        this.id = UUID.randomUUID().toString();
    }

    public WeblogPermission(Weblog weblog, User user, String actions) {
       
        super(USER_WEBLOG_PERMISSION_NAME_PREFIX + user.getUserName());
        super.setActions(actions);
        super.setObjectType(PERMISSION_OBJECT_TYPE);
        super.setObjectId(weblog.getHandle());
        super.setUserName(user.getUserName());
         this.id = UUID.randomUUID().toString();
    }

    public WeblogPermission(Weblog weblog, User user, List<String> actions) {
        super(USER_WEBLOG_PERMISSION_NAME_PREFIX + user.getUserName());
        super.setActionsAsList(actions);
        super.setObjectType(PERMISSION_OBJECT_TYPE);
        super.setObjectId(weblog.getHandle());
        super.setUserName(user.getUserName());
         this.id = UUID.randomUUID().toString();
    }
     public WeblogPermission(Weblog weblog, User user, List<String> actions, boolean pending) {
        super(USER_WEBLOG_PERMISSION_NAME_PREFIX + user.getUserName());
        super.setActionsAsList(actions);
        super.setObjectType(PERMISSION_OBJECT_TYPE);
        super.setObjectId(weblog.getHandle());
        super.setUserName(user.getUserName());
        super.setPending(pending);
         this.id = UUID.randomUUID().toString();
    }
     
    public WeblogPermission(Weblog weblog, List<String> actions) {
        super(USER_WEBLOG_PERMISSION_NAME_PREFIX + "N/A");
        super.setActionsAsList(actions);
        super.setObjectType(PERMISSION_OBJECT_TYPE);
        super.setObjectId(weblog.getHandle());
        this.id = UUID.randomUUID().toString();
    } 
    
      
    public static List<String> getAllActions(){
        return ALL_ACTIONS;
    }

    public boolean implies(PermissionEntity perm) {
        if (perm instanceof WeblogPermission) {
            WeblogPermission rperm = (WeblogPermission) perm;

            if (hasAction(ADMIN)) {
                // admin implies all other permissions
                return true;
            } else if (hasAction(POST)) {
                // Best we've got is POST, so make sure perm doesn't specify ADMIN
                for (String action : rperm.getActionsAsList()) {
                    if (action.equals(ADMIN)) {
                        return false;
                    }
                }
            } else if (hasAction(EDIT_DRAFT)) {
                // Best we've got is EDIT_DRAFT, so make sure perm doesn't specify anything else
                for (String action : rperm.getActionsAsList()) {
                    if (action.equals(POST)) {
                        return false;
                    }
                    if (action.equals(ADMIN)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof WeblogPermission)) {
            return false;
        }
        WeblogPermission other = (WeblogPermission) object;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public String toString() {
        return this.toJsonString();
    }     


}
