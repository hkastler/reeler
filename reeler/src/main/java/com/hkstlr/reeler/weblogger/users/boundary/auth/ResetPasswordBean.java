/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.users.boundary.auth;

import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.persistence.NoResultException;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
@Named
public class ResetPasswordBean {

    private static final Logger LOG = Logger.getLogger(ResetPasswordBean.class.getName());

    
    
    @Resource(name = "java:/ReelerMail")
    private Session mailSession;

    @EJB
    UserManager userManager;

    private String username;

    public ResetPasswordBean() {
        //constructor 
    }

    public void resetPassword() throws NamingException, MessagingException {
        User userToGet;
        try {
            userToGet = userManager.getUserByUserName(username);
            Logger.getLogger(ResetPasswordBean.class.getName()).log(Level.INFO, "user:{0}", userToGet.toJsonString());
            
            sendMessage(userToGet.getEmailAddress());
            

        } catch (NoResultException | NullPointerException ex) {

            Logger.getLogger(ResetPasswordBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Asynchronous
    public Future<String> sendMessage(String email) {
        String status;
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom();
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            message.setSubject("Reset Password Request");
            message.setHeader("X-Mailer", "JavaMail");
            DateFormat dateFormatter = DateFormat
                    .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
            Date timeStamp = new Date();
            String messageBody = "have some text and a password reset link here. sent:"
                    + dateFormatter.format(timeStamp);
            message.setText(messageBody);
            message.setSentDate(timeStamp);
            Transport.send(message);
            status = "Sent";
            LOG.log(Level.INFO, "Mail sent to {0}", email);
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE,"err",ex);
            status = "Encountered an error";
           
        }
        return new AsyncResult<String>(status);
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
