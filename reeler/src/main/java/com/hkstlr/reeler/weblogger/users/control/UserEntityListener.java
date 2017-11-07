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
 * 
 */
package com.hkstlr.reeler.weblogger.users.control;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.app.entities.PermissionEntity;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogPermissionManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.PostLoad;

/**
 *
 * @author henry.kastler
 */
public class UserEntityListener {

    private static final Logger log = Logger.getLogger(UserEntityListener.class.getName());
    
    
    
    @EJB
    WeblogPermissionManager wpm;
    
    @PostLoad
    public void userPostLoad(User user) throws WebloggerException {
        
        List<PermissionEntity> permissions = new ArrayList<>();
        
        List<WeblogPermission> weblogPermissions = wpm.getWeblogPermissions(user);
        for(WeblogPermission wp : weblogPermissions){
            permissions.add(wp);
        }
        user.setPermissions(permissions);
        
    }
}
