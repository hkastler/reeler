package com.hkstlr.reeler.weblogger.entities;

import java.io.Serializable;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import com.hkstlr.reeler.weblogger.boundary.manager.UserManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.users.entities.User;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
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

public class WeblogPermission extends ObjectPermission implements Serializable {

    public static final String EDIT_DRAFT = "edit_draft";
    public static final String POST = "post";
    public static final String ADMIN = "admin";
    public static final List<String> ALL_ACTIONS = new ArrayList<String>();

    protected static final long serialVersionUID = 1L;

    
    static {
        ALL_ACTIONS.add(EDIT_DRAFT);
        ALL_ACTIONS.add(POST);
        ALL_ACTIONS.add(ADMIN);
    }

    public WeblogPermission() {
        super("Weblog");
    }
    
    public WeblogPermission(String name) {
        super(name);
    }

    public WeblogPermission(Weblog weblog, User user, String actions) {
        //super("WeblogPermission user: " + user.getUserName());
        setActions(actions);
        setObjectType("Weblog");
        setObjectId(weblog.getHandle());
        setUserName(user.getUserName());
    }

    public WeblogPermission(Weblog weblog, User user, List<String> actions) {
        super("WeblogPermission user: " + user.getUserName());
        setActionsAsList(actions);
        setObjectType("Weblog");
        setObjectId(weblog.getHandle());
        setUserName(user.getUserName());
    }

    public List<WeblogPermission> getWeblogPermissions(Weblog weblog) {
        // TODO Auto-generated method stub
        return null;
    }

    public void revokeWeblogPermission(Weblog weblog, User user, List<String> allActions) {
        // TODO Auto-generated method stub

    }

    public void grantWeblogPermission(Weblog newWeblog, String creator, List<String> actions) {
        // TODO Auto-generated method stub

    }

    public List<WeblogPermission> getWeblogPermissions(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    

    public WeblogPermission(Weblog weblog, List<String> actions) {
        super("WeblogPermission user: N/A");
        setActionsAsList(actions);
        setObjectType("Weblog");
        setObjectId(weblog.getHandle());
    }

    /*public User getUser() throws WebloggerException {
    if (getUserName() != null) {
    return null;//userManager.getUserByUserName(getUserName());
    }
    return null;
    }*/

    public boolean implies(Permission perm) {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GlobalPermission: ");
        for (String action : getActionsAsList()) {
            sb.append(" ").append(action).append(" ");
        }
        return sb.toString();
    }

    /*    public boolean equals(Object other) {
    if (other == this) {
    return true;
    }
    if (!(other instanceof WeblogPermission)) {
    return false;
    }
    WeblogPermission o = (WeblogPermission)other;
    return new EqualsBuilder()
    .append(getUserName(), o.getUserName())
    .append(getObjectId(), o.getObjectId())
    .append(getActions(), o.getActions())
    .isEquals();
    }
    
    public int hashCode() {
    return new HashCodeBuilder()
    .append(getUserName())
    .append(getObjectId())
    .append(getActions())
    .toHashCode();
    }*/
   


}
