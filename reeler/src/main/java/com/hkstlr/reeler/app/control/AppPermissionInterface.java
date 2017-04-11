/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import java.security.Permission;
import java.util.List;

/**
 *
 * @author henry.kastler
 */
public interface AppPermissionInterface {

    /**
     * Merge actions into this permission.
     */
    void addActions(AppPermission perm);

    /**
     * Merge actions into this permission.
     */
    void addActions(List<String> newActions);

    String getActions();

    List<String> getActionsAsList();

    boolean hasAction(String action);

    boolean hasActions(List<String> actionsToCheck);
    /**
     * True if permission specifies no actions
     */
    boolean isEmpty();

    /**
     * Merge actions into this permission.
     */
    void removeActions(List<String> actionsToRemove);

    void setActions(String actions);

    void setActionsAsList(List<String> actionsList);
    
}
