/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
