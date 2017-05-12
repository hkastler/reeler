
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 *
 *  adapted from  org.apache.roller.weblogger.business.jpa.JPAUserManagerImpl.java;
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.GlobalPermission;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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

    private static final Logger LOG = Logger.getLogger(WeblogPermissionManager.class.getName());
    
    private static final String NOT_FOUND = "ERROR: permission not found";

    @PersistenceContext
    protected EntityManager em;

    public WeblogPermissionManager() {
        //constructor
    }

    public WeblogPermissionManager(Class<WeblogPermission> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogPermission getWeblogPermission(Weblog weblog, User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogId",
                WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.WARNING, "getWeblogPermission", e);
            return null;
        }
    }
    
    private TypedQuery<WeblogPermission> permissionQuery(){
        TypedQuery<WeblogPermission> q = em.createNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                WeblogPermission.class);
        return q;
    }

    public WeblogPermission getWeblogPermissionIncludingPending(Weblog weblog, User user) throws WebloggerException {
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.WARNING, "getWeblogPermissionIncludingPending", e);
            return null;
        }
    }

    public void grantWeblogPermission(Weblog weblog, User user, List<String> actions) throws WebloggerException {

        // first, see if user already has a permission for the specified object
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.WARNING, "WeblogPermission.getByUserName&WeblogIdIncludingPending", e);
        }

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
        TypedQuery<WeblogPermission> q = permissionQuery();        
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOG.log(Level.FINE, null, ignored);
        }

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
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm = null;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOG.log(Level.FINE, null, ignored);
        }

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
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm;
        try {
            existingPerm = q.getSingleResult();

        } catch (NoResultException ignored) {
            LOG.log(Level.FINE, null, ignored);
            throw new WebloggerException(NOT_FOUND);
        }
        // set pending to false
        existingPerm.setPending(false);
        save(existingPerm);
    }

    public void declineWeblogPermission(Weblog weblog, User user) throws WebloggerException {

        // get specified permission
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission existingPerm;
        try {
            existingPerm = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOG.log(Level.FINE, null, ignored);
            throw new WebloggerException(NOT_FOUND);
        }
        // remove permission
        this.em.remove(existingPerm);
    }

    public void revokeWeblogPermission(Weblog weblog, User user, List<String> actions) throws WebloggerException {

        // get specified permission
        TypedQuery<WeblogPermission> q = permissionQuery();
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        WeblogPermission oldperm;
        try {
            oldperm = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOG.log(Level.FINE, null, ignored);
            throw new WebloggerException(NOT_FOUND);
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
        TypedQuery<WeblogPermission> q = permissionQuery();
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
        // if user has specified permission in weblog return true
        TypedQuery<Weblog> qWeblog = em.createNamedQuery("Weblog.findById", Weblog.class);
        qWeblog.setParameter("id", perm.getObjectId());
        Weblog weblog = qWeblog.getSingleResult();
        WeblogPermission existingPerm = getWeblogPermission(weblog, user);
        if (existingPerm != null && existingPerm.implies(perm)) {
            return true;
        }

        // if Blog Server admin would still have weblog permission above
        GlobalPermission globalPerm;
        Query qGlobal = em.createNamedQuery("ObjectPermission.findByUserName", GlobalPermission.class);
        qGlobal.setParameter("userName", user.getUserName());
        globalPerm = (GlobalPermission) qGlobal.getSingleResult();
        if (globalPerm.implies(perm)) {
            return true;
        }

        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("PERM CHECK FAILED: user " + user.getUserName() + " does not have " + perm.toString());
        }
        return false;
    }
}
