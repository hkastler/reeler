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
 * derived from roller code, sometime 2017
 */
package com.hkstlr.reeler.weblogger.users.boundary.manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.entities.JdbcrealmGroup;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import com.hkstlr.reeler.weblogger.weblogs.entities.GlobalPermission;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.Validator;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class UserManager extends AbstractManager<User> {

    private static final Logger LOG = Logger.getLogger(UserManager.class.getName());
    
    

    @Resource
    Validator validator;

    @PersistenceContext
    private EntityManager em;

    public UserManager() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
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
     */
    public void addUser(User newUser) {        
        create(newUser);
    }

    /**
     * Save a user.
     *
     * @param user User to be saved.
     */
    public void saveUser(User user) {
        em.merge(user);
    }

    /**
     * Remove a user.
     *
     * @param user User to be removed.
     */
    public void removeUser(User user){
        remove(user);
    }

    /**
     * Get count of enabled users
     */
    public long getUserCount(){
        return count();
    }

    /**
     * get a user by activation code
     *
     * @param activationCode
     * @return
     * @throws WebloggerException
     */
    public User getUserByActivationCode(String activationCode){
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
    public User getUser(String id){
        return findById(id);
    }

    /**
     * Lookup a user by UserName.
     *
     * This lookup is restricted to 'enabled' users by default. So this method
     * will return null if the user is found but is not enabled.
     *
     * @param userName User Name of user to lookup.
     * @return The user, or null if not found or not enabled.
     */
    public User getUserByUserName(String userName){
        //return findByField("userName", userName);
       TypedQuery<User> uq = em.createNamedQuery("User.findByUsername", User.class);
       uq.setParameter("userName", userName);
       User returnUser = null;
       try{
            returnUser = uq.getSingleResult();
       }catch(NoResultException e){
           LOG.log(Level.FINE,"username not found",e);
           
       }catch(Exception e){
           LOG.log(Level.FINE,"error",e);
       }
       return returnUser;
    }

    /**
     * Lookup a user by UserName with the given enabled status.
     *
     * @param userName User Name of user to lookup.
     * @param enabled True if user is enabled, false otherwise.
     * @return The user, or null if not found or of the proper enabled status.
     * @throws WebloggerException If there is a problem.
     */
    public User getUserByUserName(String userName, Boolean enabled){
        Query query = getEntityManager().createNamedQuery("User.getByUserName&Enabled");
        query.setParameter(1, userName);
        query.setParameter(2, enabled);
        return (User) query.getSingleResult();
        
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
    public User getUserByOpenIdUrl(String openIdUrl){
        return findByField("openIdUrl", openIdUrl);        
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
     */
    public List<User> getUsers(
            Boolean enabled,
            Date startDate,
            Date endDate,
            int offset,
            int length){
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
     * @param enabled True for only enabled, false for disabled, null for all
     * @return List of (up to length) users that match startsWith string
     */
    public List<User> getUsersStartingWith(String startsWith,
            Boolean enabled, int offset, int length){
        return null;
    }

    /**
     * Get map with 26 entries, one for each letter A-Z and containing Longs
     * reflecting the number of users whose names start with each letter.
     */
    public Map<String, Long> getUserNameLetterMap(){
        return null;
    }

    /**
     * Get collection of users whose names begin with specified letter
     */
    public List<User> getUsersByLetter(char letter, int offset, int length){
        return null;
    }

    //--------------------------------------------------------------- role CRUD
    /**
     * Grant role to user.
     */
    public void grantRole(String roleName, User user){
        //TODO: implementation
    }

    /**
     * Revoke role from user.
     */
    public void revokeRole(String roleName, User user){
        //TODO: implementation
    }

    /**
     * Returns true if user has role specified, should be used only for testing.
     *
     * @deprecated Use checkPermission() instead.
     */
    public boolean hasRole(String roleName, User user){
        return false;
    }

    /**
     * Get roles associated with user, should be used only for testing. Get all
     * roles associated with user.
     *
     * @deprecated Use checkPermission() instead.
     */
    public List<String> getRoles(User user){
        return null;
    }
    
    public JdbcrealmGroup getAdminGroup(){
        String groupname = GlobalPermission.ADMIN;
        TypedQuery<JdbcrealmGroup> q = getJdbcrealmGroup(groupname);
        JdbcrealmGroup retGroup = null;
        
        try{
            retGroup =  q.getSingleResult();
        }catch(javax.persistence.NoResultException e){
            retGroup =  createJdbcrealmGroup(groupname);            
            LOG.log(Level.FINE,groupname.concat(" JdbcrealmGroup not found, created "));
        }catch(Exception e){
            LOG.log(Level.FINE,"error",e);
        }
        return retGroup;
    }
    
    public UserRole getRoleByName(String roleName) {
        
        UserRole retRole = null;
        TypedQuery<UserRole> q = em.createNamedQuery("UserRole.findByRolename",UserRole.class);
        q.setParameter("roleName", roleName);
        try{
            retRole =  q.getSingleResult();
        }catch(javax.persistence.NoResultException e){
            retRole =  (UserRole)createAttach(new UserRole(roleName));
            LOG.log(Level.FINE,roleName.concat(" roleName not found, created"));
        }catch(Exception e){
            LOG.log(Level.FINE,"error",e);
        }
        return retRole;
    }
    

    public TypedQuery<JdbcrealmGroup> getJdbcrealmGroup(String groupname){
        TypedQuery<JdbcrealmGroup> q = em.createNamedQuery("JdbcrealmGroup.findByGroupname", JdbcrealmGroup.class);
        q.setParameter("groupname", groupname);
        return q;
    }

    
    public JdbcrealmGroup getUserGroup(){
        String groupname = "user";
        TypedQuery<JdbcrealmGroup> q = getJdbcrealmGroup(groupname);
        JdbcrealmGroup retGroup;
        
        try{
            retGroup =  q.getSingleResult();
        }catch(javax.persistence.NoResultException e){
            retGroup =  createJdbcrealmGroup(groupname);
            LOG.log(Level.FINE,groupname.concat(" groupname not found, created"));
        }
        return retGroup;
    }


    public JdbcrealmGroup createJdbcrealmGroup(String groupname){
        JdbcrealmGroup retGroup = new JdbcrealmGroup();
        retGroup.setGroupname(groupname);
        em.persist(retGroup);
        JdbcrealmGroup attachedRetGroup = getJdbcrealmGroup(groupname).getSingleResult();
        return attachedRetGroup;
    }
    
    public Object createAttach(Object obj){        
        em.persist(obj);
        String id = StringPool.BLANK;
                          
        try {
            //this works because our expected entities 
            //extend AbstractEntity 
            Method getId = obj.getClass().getSuperclass().getMethod("getId", null);
            id = (String)getId.invoke(obj, null);
            
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object attachObj = em.find(obj.getClass(), id);
        if(attachObj == null){
            LOG.info("can't find " + obj.getClass().getName() + " by id " + id);
            return obj;
        }
        return attachObj;
    }

    
    

}
