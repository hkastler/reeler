package com.hkstlr.reeler.app.control;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.hkstlr.reeler.weblogger.entities.ObjectPermission;



public abstract class AppPermission extends java.security.Permission {
	
	private static Logger log = Logger.getLogger(AppPermission.class.getName());
    

    public AppPermission(String name) {
        super(name);
    }
   
    public abstract void setActions(String actions); 

    public abstract String getActions();

    public List<String> getActionsAsList() {
    	return Arrays.asList(getActions().split(","));
        //return Utilities.stringToStringList(getActions(), ",");
    }
    
    public void setActionsAsList(List<String> actionsList) {
        //setActions(Utilities.stringListToString(actionsList, ","));
        StringBuilder actions = new StringBuilder();
        for (String s : actionsList){
        	actions.append(s);
        	actions.append(",");
        }
        setActions(actions.toString());
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
     * Merge actions into this permission.
     */
    public void addActions(ObjectPermission perm) {
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
    public void removeActions(List<String> actionsToRemove) {
        List<String> updatedActions = getActionsAsList();
        for (String actionToRemove : actionsToRemove) {
            updatedActions.remove(actionToRemove);
        }
        log.fine("updatedActions2: " + updatedActions);
        setActionsAsList(updatedActions);
    }
    
    /**
     * True if permission specifies no actions
     */
    public boolean isEmpty() {
        return (getActions() == null || getActions().trim().length() == 0);
    }
}
