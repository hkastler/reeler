/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.admin;

import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import com.hkstlr.reeler.weblogger.users.control.PasswordDigester;
import com.hkstlr.reeler.weblogger.users.entities.JdbcrealmGroup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogCategoryManager;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class SetupBean {

    @EJB
    private transient UserManager userManager;

    @EJB
    private transient WeblogCategoryManager weblogCategoryManager;

    private User user;

    @ManagedProperty(value = "#{param.id}")
    protected String id;

    @ManagedProperty(value = "#{param.action}")
    protected String action;

    protected String actionLabel;

    private static final String PAGE_HOME = "";

    public SetupBean() {
        //constructor
    }

    @PostConstruct
    protected void init() {
        if (id == null && "edit".equals(action)) {
            User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            id = currentUser.getId();
        }

        if (id != null) {
            user = userManager.findById(id);
        } else {
            action = "create";
            this.user = new User();
        }

    }

    public void setupWeblogCategories() {
        int numberOfCats = weblogCategoryManager.count();
        if (numberOfCats == 0) {

        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getPageHome() {
        return PAGE_HOME;
    }

    public void createUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String passwordText = params.get("passwordText");
        String hashPassText = PasswordDigester.getDigestedPassword(passwordText);
        this.user.setPassword(hashPassText);

        this.user.setDateCreated(new Date());

        List<JdbcrealmGroup> jdbcrealmGroups = new ArrayList<>(1);
        String roleName = "admin";

        JdbcrealmGroup adminG = userManager.getAdminGroup();
        jdbcrealmGroups.add(adminG);

        user.setJdbcrealmGroups(jdbcrealmGroups);

        UserRole role = new UserRole(roleName, this.user.getUserName());
        Set<UserRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setIsEnabled(true);
        userManager.addUser(this.user);
        FacesMessageManager.addSuccessMessage(null, "User created");
    }

}
