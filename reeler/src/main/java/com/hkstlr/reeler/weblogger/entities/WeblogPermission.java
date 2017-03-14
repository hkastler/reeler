package com.hkstlr.reeler.weblogger.entities;

import java.io.Serializable;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.hkstlr.reeler.app.control.WebloggerException;
//import com.hkstlr.reeler.weblogger.boundary.manager.UserManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.entities.ObjectPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.entities.Weblog;

public class WeblogPermission extends ObjectPermission implements Serializable {
    public static final String EDIT_DRAFT = "edit_draft";
    public static final String POST = "post";
    public static final String ADMIN = "admin";
    public static final List<String> ALL_ACTIONS = new ArrayList<String>();
    
    //@Inject
    //UserManager userManager;
    
    //@Inject
    //WeblogManager weblogManager;
    
    static {
        ALL_ACTIONS.add(EDIT_DRAFT);
        ALL_ACTIONS.add(POST);
        ALL_ACTIONS.add(ADMIN);
    }

    public WeblogPermission() {
        // required by JPA
    }

    public WeblogPermission(Weblog weblog, User user, String actions) {
        super("WeblogPermission user: " + user.getUserName());
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
    
    public void setActionsAsList(List<String> actions) {
		// TODO Auto-generated method stub
		
	}

	public WeblogPermission(Weblog weblog, List<String> actions) {
        super("WeblogPermission user: N/A");
        setActionsAsList(actions);
        setObjectType("Weblog");
        setObjectId(weblog.getHandle());
    }
    
    public Weblog getWeblog() throws WebloggerException {
        if (getObjectId() != null) {
            return null;//weblogManager.getWeblogByHandle(objectId, null);
        }
        return null;
    }

    public User getUser() throws WebloggerException {
        if (getUserName() != null) {
            return null;//userManager.getUserByUserName(getUserName());
        }
        return null;
    }

    public boolean implies(Permission perm) {
        if (perm instanceof WeblogPermission) {
            WeblogPermission rperm = (WeblogPermission)perm;
            
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