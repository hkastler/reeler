/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.admin;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@RolesAllowed("admin")
@ManagedBean
@RequestScoped
public class UserAdminBean {

    @Inject
    private Weblogger weblogger;
    
    @Inject
    private AdminUIBean adminUIBean;

    private static final Logger log = Logger.getLogger(UserAdminBean.class.getName());

    private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

    public UserAdminBean() {
    }

    public void updateUser() {
        log.info("user updated");  
        FacesMessageManager.addErrorMessage("Not Yet Implemented");
        //FacesMessageManager.addSuccessMessage("userMessages", "User updated");
        //StringBuilder actionPath = new StringBuilder();        
        //actionPath.append(adminUIBean.getPath()).append("/users.xhtml?faces-redirect=true&amp;includeViewParams=true");       
        //return actionPath.toString();
    }
}
