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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
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

    @EJB
    private transient UserManager userManager;    

    
    private transient URLStrategy urlStrategy;

    
    private static final Logger log = Logger.getLogger(LoginBean.class.getName());


    /**
     * Creates a new instance of LoginBean
     * @param userManager
     * @param urlStrategy
     */
    
    @Inject 
    public LoginBean(URLStrategy urlStrategy) {
        this.urlStrategy = urlStrategy;
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
    public void validateUser() throws WebloggerException {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = getUser();
       

        String hashPwd = password;
        
        try {
            hashPwd = PasswordDigester.getDigestedPassword(password);
        } catch (Exception ex) {
            
            log.log(Level.SEVERE, null, ex);
        }

        if ((user != null && user.getPassword().equals(hashPwd))) {
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);

            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            
            if (request.getUserPrincipal() != null) {
                try {
                    request.logout();
                } catch (ServletException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String outcome = "/weblogger/login/index";
            
            try {
                request.login(this.username, this.password);
                
                outcome = urlStrategy.getLoginSuccessOutcome(user); // Do your thing?
            } catch (ServletException e) {
                log.log(Level.SEVERE,"user not realm authenticated:",e);
                context.addMessage(null, new FacesMessage("Login failed." + e.getMessage()));
                
            }

            log.log(Level.INFO,"user:" + user.toJsonString());
            log.log(Level.INFO, "user?{0}", request.isUserInRole("user"));
            log.log(Level.INFO, "admin?{0}", request.isUserInRole("admin"));

            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);

        } else {
            FacesMessage message;
            if (user != null) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username '"
                                + username
                                + "' supplied wrong password.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username '"
                                + username
                                + "' does not exist.");
            }
            context.addMessage(null, message);
            
        }
    }

    private User getUser() {
        User userToGet = null;
        try {
            userToGet = userManager.getUserByUserName(username);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "user:{0}", userToGet.toJsonString());
            return userToGet;

        } catch (NoResultException | WebloggerException | NullPointerException ex) {

            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
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
        
        return "/index?faces-redirect=true";

    }

}
