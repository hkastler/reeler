package com.hkstlr.reeler.app.control;

import java.security.Permission;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import java.util.Date;

public class AppPermission extends java.security.Permission implements AppPermissionInterface {

    private static final Logger LOG = Logger.getLogger(AppPermission.class.getName());

    public AppPermission(String name) {
        super(name);
    }
    
    public AppPermission(String userName, Date dateCreated) {
        super(userName);
    }

    @Override
    public List<String> getActionsAsList() {
        return Arrays.asList(getActions().split(","));
        //return Utilities.stringToStringList(getActions(), ",");
    }

    @Override
    public void setActionsAsList(List<String> actionsList) {
        //setActions(Utilities.stringListToString(actionsList, ","));
        StringBuilder actions = new StringBuilder();
        for (String s : actionsList) {
            actions.append(s);
            actions.append(",");
        }
        setActions(actions.toString());
    }

    
    public boolean hasAction(String action) {
        List<String> actionList = getActionsAsList();
        return actionList.contains(action);
    }

    @Override
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
     * Merge actions into this permission.
     */
    public void addActions(AppPermission perm) {
        List<String> newActions = perm.getActionsAsList();
        List<String> updatedActions = getActionsAsList();
        for (String newAction : newActions) {
            if (!updatedActions.contains(newAction)) {
                updatedActions.add(newAction);
            }
        }
        setActionsAsList(updatedActions);
    }

    /**
     * Merge actions into this permission.
     */
    @Override
    public void addActions(List<String> newActions) {
        List<String> updatedActions = getActionsAsList();
        for (String newAction : newActions) {
            if (!updatedActions.contains(newAction)) {
                updatedActions.add(newAction);
            }
        }
        setActionsAsList(updatedActions);
    }

    /**
     * Merge actions into this permission.
     */
    @Override
    public void removeActions(List<String> actionsToRemove) {
        List<String> updatedActions = getActionsAsList();
        for (String actionToRemove : actionsToRemove) {
            updatedActions.remove(actionToRemove);
        }
        LOG.fine("updatedActions2: " + updatedActions);
        setActionsAsList(updatedActions);
    }

    /**
     * True if permission specifies no actions
     * @return 
     */
    @Override
    public boolean isEmpty() {
        return (getActions() == null || getActions().trim().length() == 0);
    }

    @Override
    public boolean implies(Permission permission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActions(String actions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
