/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.users.boundary.login;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.URLStrategy;
import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import com.hkstlr.reeler.weblogger.users.control.PasswordDigester;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;


import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
@Named
public class LoginBean implements Serializable {

    private String username;
    private String password;

    public static final String USER_SESSION_KEY = "user";

    @Inject
    private UserManager userManager;

    @Inject
    private transient Logger log;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Validates the user. If the user doesn't exist or the password is
     * incorrect, the appropriate message is added to the current
     * <code>FacesContext</code>. If the user successfully authenticates,
     * navigate them to the page referenced by the outcome
     * <code>app-main</code>.
     * </p>
     *
     */
    public void validateUser() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = getUser();
        boolean isPasswordOK = false;

        String hashPwd = password;
        log.log(Level.INFO, "password is:" + hashPwd);
        try {
            hashPwd = PasswordDigester.getDigestedPassword(password);
        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
        }

        if (user != null && user.getPassword().equals(hashPwd)) {
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);

            log.info("user now authenticated");
            log.info(user.toString());

            //FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            Principal userPrincipal = request.getUserPrincipal();
            if (request.getUserPrincipal() != null) {
                request.logout();
            }
            String outcome = "/weblogger/login/index";
            //log.log(Level.INFO, "login password:{0}", password);
            try {
                request.login(this.username, this.password);
                log.info("user authenticated at request level");
                outcome = URLStrategy.getLoginSuccessOutcome(); // Do your thing?
            } catch (ServletException e) {
                log.log(Level.SEVERE,"user not realm authenticated:",e);
                context.addMessage(null, new FacesMessage("Login failed." + e.getMessage()));
                //return "common/error";
            }

            log.log(Level.INFO, "roles:{0}", Arrays.toString(user.getJdbcrealmGroups().toArray()));
            log.log(Level.INFO, "user?{0}", request.isUserInRole("user"));
            log.log(Level.INFO, "admin?{0}", request.isUserInRole("admin"));

            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);

        } else {
            FacesMessage message = null;
            if (!isPasswordOK) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username '"
                        + username
                        + "' does not exist.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username '"
                        + username
                        + "' supplied wrong password.");
            }
            context.addMessage(null, message);
            //return null;
        }
    }

    private User getUser() {
        User userToGet = null;
        try {
            userToGet = userManager.getUserByUserName(username);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "user:{0}", userToGet.toString());
            return userToGet;
        } catch (NoResultException nre) {
            //return null;
        } catch (WebloggerException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe){
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, null, npe);
        }
        return userToGet;
    }

    /**
     * <p>
     * When invoked, it will invalidate the user's session and move them to the
     * homepage.</p>
     *
     * @return <code>home</code>
     */
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return "/index?faces-redirect=true";

    }

}
