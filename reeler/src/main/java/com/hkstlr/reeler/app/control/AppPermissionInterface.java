/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * derived from roller code
 */
package com.hkstlr.reeler.app.control;

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
