/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.users.boundary.manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Validator;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class UserManager extends AbstractManager<User> {

    @Resource
    Validator validator;

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserManager() {
        super(User.class);
    }

    //--------------------------------------------------------------- user CRUD    
    /**
     * Add a new user.
     *
     * This method is used to provide supplemental data to new user accounts,
     * such as adding the proper roles for the user. This method should see if
     * the new user is the first user and give that user the admin role if so.
     *
     * @param newUser User object to be added.
     * @throws WebloggerException If there is a problem.
     */
    public void addUser(User newUser) throws WebloggerException {
        create(newUser);
    }

    /**
     * Save a user.
     *
     * @param user User to be saved.
     * @throws WebloggerException If there is a problem.
     */
    public void saveUser(User user) throws WebloggerException {
    }

    /**
     * Remove a user.
     *
     * @param user User to be removed.
     * @throws WebloggerException If there is a problem.
     */
    
    public void removeUser(User user) throws WebloggerException {
        remove(user);
    }

    /**
     * Get count of enabled users
     */
    public long getUserCount() throws WebloggerException {
        return count();
    }

    /**
     * get a user by activation code
     *
     * @param activationCode
     * @return
     * @throws WebloggerException
     */
    public User getUserByActivationCode(String activationCode)
            throws WebloggerException {
        User user = findByField("activationCode", activationCode);
        return user;
    }

    //------------------------------------------------------------ user queries
    /**
     * Retrieve a user by its internal identifier id.
     *
     * @param id the id of the user to retrieve.
     * @return the user object with specified id or null if not found
     * @throws WebloggerException
     */
    public User getUser(String id) throws WebloggerException {
        User user = findById(id);
        return user;
    }

    /**
     * Lookup a user by UserName.
     *
     * This lookup is restricted to 'enabled' users by default. So this method
     * will return null if the user is found but is not enabled.
     *
     * @param userName User Name of user to lookup.
     * @return The user, or null if not found or not enabled.
     * @throws WebloggerException If there is a problem.
     */
    public User getUserByUserName(String userName) throws WebloggerException {
        User user = findByField("userName", userName);
        return user;
    }

    /**
     * Lookup a user by UserName with the given enabled status.
     *
     * @param userName User Name of user to lookup.
     * @param enabled True if user is enabled, false otherwise.
     * @return The user, or null if not found or of the proper enabled status.
     * @throws WebloggerException If there is a problem.
     */
    public User getUserByUserName(String userName, Boolean enabled)
            throws WebloggerException {
        Query query = getEntityManager().createNamedQuery("User.getByUserName&Enabled");
        query.setParameter(1, userName);
        query.setParameter(2, enabled);
        User user = (User) query.getSingleResult();
        return user;
    }

    /**
     * Lookup a user by Open ID URL.
     *
     * This lookup is restricted to 'enabled' users by default. So this method
     * will return null if the user is found but is not enabled.
     *
     * @param openIdUrl OpenIdUrl of user to lookup.
     * @return The user, or null if not found or not enabled.
     * @throws WebloggerException If there is a problem.
     */
    public User getUserByOpenIdUrl(String openIdUrl)
            throws WebloggerException {
        User user = findByField("openIdUrl", openIdUrl);
        return user;
    }

    /**
     * Lookup a group of users.
     *
     * The lookup may be constrained to users with a certain enabled status, to
     * users created within a certain date range, and the results can be
     * confined to a certain offset & length for paging abilities.
     *
     * @param enabled True for enabled only, False for disabled only (or null
     * for all)
     * @param startDate Restrict to those created after startDate (or null for
     * all)
     * @param endDate Restrict to those created before startDate (or null for
     * all)
     * @param offset The index of the first result to return.
     * @param length The number of results to return.
     * @return List A list of UserDatUsers which match the criteria.
     * @throws WebloggerException If there is a problem.
     */
    
    public List<User> getUsers(
            Boolean enabled,
            Date startDate,
            Date endDate,
            int offset,
            int length) throws WebloggerException {
        List<User> userList;
        Query q = getNamedQuery("User.getByEnabled&EndDate&StartDateOrderByStartDateDesc");
        q.setParameter(1, enabled);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);
        List<User> users = (List<User>) q.getResultList();
        userList = users.subList(offset, length);

        return userList;
    }

    /**
     * Lookup users whose usernames or email addresses start with a string.
     *
     * @param startsWith String to match userNames and emailAddresses against
     * @param offset Offset into results (for paging)
     * @param length Max to return (for paging)
     * @param enabled True for only enalbed, false for disabled, null for all
     * @return List of (up to length) users that match startsWith string
     */
    public List<User> getUsersStartingWith(String startsWith,
            Boolean enabled, int offset, int length) throws WebloggerException {
        return null;
    }

    /**
     * Get map with 26 entries, one for each letter A-Z and containing Longs
     * reflecting the number of users whose names start with each letter.
     */
    public Map<String, Long> getUserNameLetterMap() throws WebloggerException {
        return null;
    }

    /**
     * Get collection of users whose names begin with specified letter
     */
    public List<User> getUsersByLetter(char letter, int offset, int length)
            throws WebloggerException {
        return null;
    }

    

    //--------------------------------------------------------------- role CRUD
    /**
     * Grant role to user.
     */
    public void grantRole(String roleName, User user) throws WebloggerException {
    }

    /**
     * Revoke role from user.
     */
    public void revokeRole(String roleName, User user) throws WebloggerException {
    }

    /**
     * Returns true if user has role specified, should be used only for testing.
     *
     * @deprecated Use checkPermission() instead.
     */
    public boolean hasRole(String roleName, User user) throws WebloggerException {
        return false;
    }

    /**
     * Get roles associated with user, should be used only for testing. Get all
     * roles associated with user.
     *
     * @deprecated Use checkPermission() instead.
     */
    public List<String> getRoles(User user) throws WebloggerException {
        return null;
    }


}
