/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.ObjectPermission;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogPermissionManager extends AbstractManager<WeblogPermission> {

    @Inject
    private Logger log;

    @PersistenceContext
    private EntityManager em;

    public WeblogPermissionManager() {
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogPermissionManager(Class<WeblogPermission> entityClass) {
        super(entityClass);
    }
    


    
    public WeblogPermission getWeblogPermission(Weblog weblog, User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogId"
                , WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    public WeblogPermission getWeblogPermissionIncludingPending(Weblog weblog, User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    public void grantWeblogPermission(Weblog weblog, User user, List<String> actions) throws WebloggerException {

        // first, see if user already has a permission for the specified object
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {}

        // permission already exists, so add any actions specified in perm argument
        if (existingPerm != null) {
            existingPerm.addActions(actions);
            
            save(existingPerm);
        } else {
            // it's a new permission, so store it
            WeblogPermission perm = new WeblogPermission(weblog, user, actions);
            save(perm);
        }
    }
    
    public void grantWeblogPermission(Weblog weblog, User user, List<String> actions, boolean pending) throws WebloggerException {

        // first, see if user already has a permission for the specified object
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {}

        // permission already exists, so add any actions specified in perm argument
        if (existingPerm != null) {
            existingPerm.addActions(actions);
            
            save(existingPerm);
        } else {
            // it's a new permission, so store it
            WeblogPermission perm = new WeblogPermission(weblog, user, actions, pending);
            save(perm);
        }
    }

    
    public void grantWeblogPermissionPending(Weblog weblog, User user, List<String> actions) throws WebloggerException {

        // first, see if user already has a permission for the specified object
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {}

        // permission already exists, so complain 
        if (existingPerm != null) {
            throw new WebloggerException("Cannot make existing permission into pending permission");

        } else {
            // it's a new permission, so store it
            WeblogPermission perm = new WeblogPermission(weblog, user, actions);
            save(perm);
        }
    }

    
    public void confirmWeblogPermission(Weblog weblog, User user) throws WebloggerException {

        // get specified permission
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm;
        try {
            existingPerm = q.getSingleResult();

        } catch (NoResultException ignored) {
            throw new WebloggerException("ERROR: permission not found");
        }
        // set pending to false
        existingPerm.setPending(false);
        save(existingPerm);
    }

    
    public void declineWeblogPermission(Weblog weblog, User user) throws WebloggerException {

        // get specified permission
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {
            throw new WebloggerException("ERROR: permission not found");
        }
        // remove permission
        this.em.remove(existingPerm);
    }

    
    public void revokeWeblogPermission(Weblog weblog, User user, List<String> actions) throws WebloggerException {

        // get specified permission
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission oldperm;
        try {
            oldperm = q.getSingleResult();
        } catch (NoResultException ignored) {
            throw new WebloggerException("ERROR: permission not found");
        }

        // remove actions specified in perm argument
        oldperm.removeActions(actions);

        if (oldperm.isEmpty()) {
            // no actions left in permission so remove it
            this.em.remove(oldperm);
        } else {
            // otherwise save it
            save(oldperm);
        }
    }

    
    public List<WeblogPermission> getWeblogPermissions(User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        return q.getResultList();
    }

    public List<WeblogPermission> getWeblogPermissions(Weblog weblog) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByWeblogId",
                WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }

    public List<WeblogPermission> getWeblogPermissionsIncludingPending(Weblog weblog) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByWeblogIdIncludingPending",
                WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }

    public List<WeblogPermission> getPendingWeblogPermissions(User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&Pending",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        return q.getResultList();
    }

    public List<WeblogPermission> getPendingWeblogPermissions(Weblog weblog) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByWeblogId&Pending",
                WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }
    
    
    public boolean checkPermission(WeblogPermission perm, User user) throws WebloggerException {

        // if permission a weblog permission
        if (perm instanceof WeblogPermission) {
            // if user has specified permission in weblog return true
            Query qWeblog = em.createNamedQuery("Weblog.findById", Weblog.class);
            qWeblog.setParameter("id", perm.getObjectId());
            Weblog weblog = (Weblog)qWeblog.getResultList();
            WeblogPermission existingPerm = getWeblogPermission(weblog, user);
            if (existingPerm != null && existingPerm.implies(perm)) {
                return true;
            }
        }

        // if Blog Server admin would still have weblog permission above
        ObjectPermission globalPerm ;
        Query qGlobal = em.createNamedQuery("ObjectPermission.findByUserName", ObjectPermission.class);
        qGlobal.setParameter("userName", user.getUserName());
        globalPerm = (ObjectPermission)qGlobal.getSingleResult();
        if (globalPerm.implies(perm)) {
            return true;
        }

        if (log.isLoggable(Level.FINE)) {
            log.fine("PERM CHECK FAILED: user " + user.getUserName() + " does not have " + perm.toString());
        }
        return false;
    }
}
