/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.admin;

import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import com.hkstlr.reeler.weblogger.users.control.PasswordDigester;
import com.hkstlr.reeler.weblogger.users.entities.JdbcrealmGroup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */

@ManagedBean
@RequestScoped
public class UserAdminBean {

    @EJB
    private transient UserManager userManager;
    
    @Inject
    private transient AdminUIBean adminUIBean;
    
    private static final String PAGE_HOME = "/weblogger/reeler-ui/admin";
        
    private User user;
    
    @ManagedProperty(value = "#{param.id}")
    protected String id;
    
    @ManagedProperty(value = "#{param.action}")
    protected String action;
    
    protected String actionLabel;

    private static final Logger LOG = Logger.getLogger(UserAdminBean.class.getName());

    public UserAdminBean() {
        //default constructor
    }
    
    @PostConstruct
    protected void init(){  
        
        if(id == null && "edit".equals(action)){
           User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
           id = currentUser.getId();
        }
        
        if(id !=null){            
            user = userManager.findById(id);
        }else{    
            action = "create";
            this.user = new User();
        }
        setActionLabel();
    }

    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getPageHome(){
        return PAGE_HOME;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(String actionLabel) {
        this.actionLabel = actionLabel;
    }
    
    public void setActionLabel(){
        if("create".equals(action)){
            actionLabel = "Create";
        }else if("edit".equals(action)){
            actionLabel = "Edit";
        }
    }
    
    @RolesAllowed("admin")
    public List<User> getUsers(){
        return userManager.findAll();
    }
    
    @RolesAllowed("admin")
    public void createUser() throws UnsupportedEncodingException, NoSuchAlgorithmException{
        
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        
        String passwordText = params.get("passwordText");
        String hashPassText = PasswordDigester.getDigestedPassword(passwordText);        
        this.user.setPassword(hashPassText);
        
        this.user.setDateCreated(new Date());
        
        //check for isAdmin
        Boolean isAdmin = Boolean.parseBoolean(params.get("isAdministrator"));
        
        List<JdbcrealmGroup> jdbcrealmGroups = new ArrayList<>(1);
        String roleName = "user";
        if(isAdmin){
            roleName = "admin";
            JdbcrealmGroup adminG = userManager.getAdminGroup();
            jdbcrealmGroups.add(adminG);
        }else{
            JdbcrealmGroup userG = userManager.getUserGroup();
            jdbcrealmGroups.add(userG);
        }
        user.setJdbcrealmGroups(jdbcrealmGroups);
        
        UserRole role = new UserRole(roleName, this.user.getUserName());
        Set<UserRole> roles = new HashSet<>(); 
        roles.add(role);
        user.setRoles(roles);
        
            
            
        userManager.addUser(this.user);
        FacesMessageManager.addSuccessMessage(null, "User created");
    }
    
    @RolesAllowed("user")
    public void updateUser() {       
        System.out.println("updating user");
        userManager.edit(this.user);
        FacesMessageManager.addSuccessMessage(null, "User info updated");        
    }
}
