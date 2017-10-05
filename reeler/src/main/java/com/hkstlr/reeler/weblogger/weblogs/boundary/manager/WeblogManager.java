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
 * adapted from org.apache.roller.weblogger.business.jpa.JPAWeblogManagerImpl.java
**
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.media.boundary.manager.MediaFileManager;
import com.hkstlr.reeler.weblogger.pings.boundary.AutoPingManager;
import com.hkstlr.reeler.weblogger.pings.boundary.PingTargetManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.GlobalPermission;
import com.hkstlr.reeler.weblogger.weblogs.control.StatCount;
import com.hkstlr.reeler.weblogger.weblogs.control.StatCountCountComparator;
import com.hkstlr.reeler.weblogger.weblogs.control.TagStat;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmarkFolder;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTagAggregate;

import com.hkstlr.reeler.weblogger.pings.entities.AutoPing;
import com.hkstlr.reeler.weblogger.pings.entities.PingQueueEntry;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;

import java.util.HashMap;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogManager extends AbstractManager<Weblog> {
    // cached mapping of weblogHandles -> weblogIds

    private Map<String, String> weblogHandleToIdMap = new HashMap<>();

    private String APPEND_AND = " AND ";

    @PersistenceContext
    EntityManager em;

    @EJB
    UserManager userManager;

    @EJB
    WeblogEntryManager weblogEntryManager;

    @EJB
    WeblogPermissionManager weblogPermissionManager;

    @EJB
    AutoPingManager autoPingManager;

    @EJB
    PingTargetManager pingTargetManager;
    
    @EJB
    MediaFileManager mediaFileManager;
    
    private static final Logger LOG = Logger.getLogger(WeblogManager.class.getName());
    
    public WeblogManager() {
        super(Weblog.class);        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * add weblog.
     */
    public void addWeblog(Weblog weblog, User user) throws WebloggerException {
        weblog.setCreator(user.getUserName());
        
        //for backwards compatibility with roller
        weblog.setEditorTheme("basic");
        //addWeblog.merge1
        em.persist(weblog);
        em.flush();
        addPermission(weblog, user);        
        addWeblogContents(weblog);
    }
    
    private boolean addPermission(Weblog newWeblog, User user){
        // grant weblog creator ADMIN permission
        List<String> actions = new ArrayList<>();
        actions.add(WeblogPermission.ADMIN);
        
        try {
            weblogPermissionManager.grantWeblogPermission(
                    newWeblog, user, actions, false);
            return true;
        } catch (WebloggerException e) {
            LOG.log(Level.WARNING,null,e);
            return false;            
        }
        
    }

    private void addWeblogContents(Weblog newWeblog)
            throws WebloggerException {

        String cats = WebloggerConfig.getProperty("newuser.categories");
        WeblogCategory firstCat = null;
        if (cats != null) {
            String[] splitcats = cats.split(",");
            for (String cat : splitcats) {
                if (cat.trim().length() == 0) {
                    continue;
                }                
                
                WeblogCategory c = new WeblogCategory(
                        newWeblog,
                        cat,
                        "description",
                        "image");
                if (firstCat == null) {
                    firstCat = c;
                }
                em.merge(c);
                em.flush();
                WeblogCategory attachedCat = em.find(WeblogCategory.class, c.getId());
                newWeblog.getWeblogCategories().add(attachedCat);
                
            }
        }

        // Use first category as default for Blogger API
        if (firstCat != null) {
            newWeblog.setBloggerCategory(firstCat);
        }
        

        // add default bookmarks
        WeblogBookmarkFolder defaultFolder = new WeblogBookmarkFolder(
                "default", newWeblog);
        //addWeblog.merge2
        this.em.merge(defaultFolder);

        String blogroll = WebloggerConfig.getProperty("newuser.blogroll");
        if (blogroll != null) {
            String[] splitroll = blogroll.split(",");
            for (String splitItem : splitroll) {
                String[] rollitems = splitItem.split("\\|");
                if (rollitems.length > 1) {
                    WeblogBookmark b = new WeblogBookmark(
                            defaultFolder,
                            rollitems[0],
                            "",
                            rollitems[1].trim(),
                            null,
                            null);
                    //this should also be counted
                    //in the unit tests
                    //but WebloggerConfig stands in the way
                    this.em.merge(b);
                }
            }
        }
        // add any auto enabled ping targets
        for (PingTarget pingTarget : this.pingTargetManager.getCommonPingTargets()) {
            if (pingTarget.isAutoEnabled()) {
                AutoPing autoPing = new AutoPing(pingTarget, newWeblog);
                this.autoPingManager.saveAutoPing(autoPing);
            }
        }
        
        //addWeblog.merge3
        this.em.merge(newWeblog);
        this.em.flush();
    }

    public void removeWeblog(Weblog weblog) throws WebloggerException {
        // remove contents first, then remove weblog
        this.removeWeblogContents(weblog);
        this.em.remove(weblog);

        // remove entry from cache mapping
        this.weblogHandleToIdMap.remove(weblog.getHandle());
    }

    /**
     * convenience method for removing contents of a weblog. TODO BACKEND: use
     * manager methods instead of queries here
     */
    private void removeWeblogContents(Weblog weblog) throws WebloggerException {

        // remove tags
        TypedQuery<WeblogEntryTag> tagQuery = em.createNamedQuery("WeblogEntryTag.findByWebsite",
                WeblogEntryTag.class);
        tagQuery.setParameter(1, weblog);
        List<WeblogEntryTag> results = tagQuery.getResultList();

        for (WeblogEntryTag tagData : results) {
            if (tagData.getWeblogEntry() != null) {
                tagData.getWeblogEntry().getTags().remove(tagData);
            }
            this.em.remove(tagData);
        }

        // remove site tag aggregates
        List<TagStat> tags = weblogEntryManager.getTags(weblog, null, null, 0, -1);
        updateTagAggregates(tags);

        // delete all weblog tag aggregates
        Query removeAggs = em.createNamedQuery(
                "WeblogEntryTagAggregate.removeByWeblog");
        removeAggs.setParameter(1, weblog);
        removeAggs.executeUpdate();

        // delete all bad counts
        Query removeCounts = em.createNamedQuery(
                "WeblogEntryTagAggregate.removeByTotalLessEqual");
        removeCounts.setParameter(1, 0);
        removeCounts.executeUpdate();

        // Remove the weblog's ping queue entries
        TypedQuery<PingQueueEntry> q = em.createNamedQuery(
                "PingQueueEntry.getByWebsite", PingQueueEntry.class);
        q.setParameter(1, weblog);
        List queueEntries = q.getResultList();
        for (Object obj : queueEntries) {
            this.em.remove(obj);
        }

        // Remove the weblog's auto ping configurations
        List<AutoPing> autopings = autoPingManager.getAutoPingsByWebsite(weblog);
        for (AutoPing autoPing : autopings) {
            this.em.remove(autoPing);
        }

       
        // remove folders (including bookmarks)
        TypedQuery<WeblogBookmarkFolder> folderQuery = em.createNamedQuery(
                "WeblogBookmarkFolder.getByWebsite", WeblogBookmarkFolder.class);
        folderQuery.setParameter(1, weblog);
        List<WeblogBookmarkFolder> folders = folderQuery.getResultList();
        for (WeblogBookmarkFolder wbf : folders) {
            this.em.remove(wbf);
        }

        // remove mediafile metadata
        // remove uploaded files
        mediaFileManager.removeAllFiles(weblog);
        
        // remove entries
        TypedQuery<WeblogEntry> refQuery = em.createNamedQuery(
                "WeblogEntry.getByWebsite", WeblogEntry.class);
        refQuery.setParameter(1, weblog);
        List<WeblogEntry> entries = refQuery.getResultList();
        for (WeblogEntry entry : entries) {
            weblogEntryManager.removeWeblogEntry(entry);
        }

        // delete all weblog categories
        Query removeCategories = em.createNamedQuery("WeblogCategory.removeByWeblog");
        removeCategories.setParameter(1, weblog);
        removeCategories.executeUpdate();

        // remove permissions
        for (WeblogPermission perm : weblogPermissionManager.getWeblogPermissions(weblog)) {
            User permUser = userManager.getUserByUserName(perm.getUserName());
            weblogPermissionManager.revokeWeblogPermission(weblog, 
                    permUser, 
                    WeblogPermission.getAllActions());
        }

    }

    protected void updateTagAggregates(List<TagStat> tags) throws WebloggerException {
        for (TagStat stat : tags) {
            TypedQuery<WeblogEntryTagAggregate> query = getNamedQueryCommitFirst(
                    "WeblogEntryTagAggregate.getByName&WebsiteNullOrderByLastUsedDesc", WeblogEntryTagAggregate.class);
            query.setParameter(1, stat.getName());
            try {
                WeblogEntryTagAggregate agg = query.getSingleResult();
                agg.setTotal(agg.getTotal() - stat.getCount());
            } catch (NoResultException ignored) {
                // nothing to update
                LOG.log(Level.FINEST,null,ignored);
            }
        }
    }

    public void saveWeblog(Weblog weblog) throws WebloggerException {

        weblog.setLastModified(new java.util.Date());
        em.merge(weblog);
    }

    

    public void addWeblog(Weblog newWeblog) throws WebloggerException {
        this.em.persist(newWeblog);
        this.em.flush();
        this.addWeblogContents(newWeblog);
    }

    

    public Weblog getWeblog(String id) throws WebloggerException {
        return this.em.find(Weblog.class, id);
    }

    public Weblog findByHandle(String handle) throws WebloggerException {
        TypedQuery<Weblog> query = getNamedQuery("Weblog.findByHandle", Weblog.class);
        query.setParameter("handle", handle);
        Weblog weblog;
        try {
            weblog = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.WARNING,"findByHandle",e);
            weblog = null;
        }

        return weblog;
    }

    public Weblog getWeblogByHandle(String handle) throws WebloggerException {
        return getWeblogByHandle(handle, Boolean.TRUE);
    }

    /**
     * Return weblog specified by handle.
     * @param handle
     * @param visible
     * @return 
     * @throws com.hkstlr.reeler.app.control.WebloggerException 
     */
    public Weblog getWeblogByHandle(String handle, Boolean visible)
            throws WebloggerException {

        if (handle == null) {
            throw new WebloggerException("Handle cannot be null");
        }

        // check cache first
        // NOTE: if we ever allow changing handles then this needs updating
        if (this.weblogHandleToIdMap.containsKey(handle)) {

            Weblog weblog = this.getWeblog(this.weblogHandleToIdMap.get(handle));
            if (weblog != null) {
                // only return weblog if enabled status matches
                if (visible == null || visible.equals(weblog.isVisible())) {
                    LOG.fine("weblogHandleToId CACHE HIT - " + handle);
                    return weblog;
                }
            } else {
                // mapping hit with lookup miss?  mapping must be old, remove it
                this.weblogHandleToIdMap.remove(handle);
            }
        }

        TypedQuery<Weblog> query = getNamedQuery("Weblog.getByHandle", Weblog.class);
        query.setParameter(1, handle);
        Weblog weblog;
        try {
            weblog = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.WARNING, "getByHandle", e);
            weblog = null;
        }

        // add mapping to cache
        if (weblog != null) {
            LOG.fine("weblogHandleToId CACHE MISS - " + handle);
            this.weblogHandleToIdMap.put(weblog.getHandle(), weblog.getId());
        }

        if (weblog != null
                && (visible == null || visible.equals(weblog.isVisible()))) {
            return weblog;
        } else {
            return null;
        }
    }

    /**
     * Get weblogs of a user
     * @param enabled
     * @param active
     * @param startDate
     * @param endDate
     * @param offset
     * @param length
     * @return 
     * @throws com.hkstlr.reeler.app.control.WebloggerException 
     */
    public List<Weblog> getWeblogs(
            Boolean enabled, Boolean active,
            Date startDate, Date endDate, int offset, int length) throws WebloggerException {

        
        List<Object> params = new ArrayList<>();
        int size = 0;
        String queryString;
        StringBuilder whereClause = new StringBuilder();

        queryString = "SELECT w FROM Weblog w WHERE ";

        if (startDate != null) {
            Timestamp start = new Timestamp(startDate.getTime());
            if (whereClause.length() > 0) {
                whereClause.append(APPEND_AND);
            }
            params.add(size++, start);
            whereClause.append(" w.dateCreated > ?").append(size);
        }
        if (endDate != null) {
            Timestamp end = new Timestamp(endDate.getTime());
            if (whereClause.length() > 0) {
                whereClause.append(APPEND_AND);
            }
            params.add(size++, end);
            whereClause.append(" w.dateCreated < ?").append(size);
        }
        if (enabled != null) {
            if (whereClause.length() > 0) {
                whereClause.append(APPEND_AND);
            }
            params.add(size++, enabled);
            whereClause.append(" w.visible = ?").append(size);
        }
        if (active != null) {
            if (whereClause.length() > 0) {
                whereClause.append(APPEND_AND);
            }
            params.add(size++, active);
            whereClause.append(" w.isActive = ?").append(size);
        }

        whereClause.append(" ORDER BY w.dateCreated DESC");

        TypedQuery<Weblog> query = em.createQuery(queryString + whereClause.toString(), Weblog.class);
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }

        return query.getResultList();
    }

    @Transactional
    public List<Weblog> getUserWeblogs(User user, boolean enabledOnly) throws WebloggerException {
        List<Weblog> weblogs = new ArrayList<>();
        if (user == null) {
            return weblogs;
        }
        List<WeblogPermission> perms = weblogPermissionManager.getWeblogPermissions(user);
        for (WeblogPermission perm : perms) {
            Weblog weblog = getWeblogByHandle(perm.getObjectId());
            if ((!enabledOnly || weblog.isVisible()) && weblog.isActive()) {
                weblogs.add(weblog);
            }
        }
        return weblogs;
    }

    public List<User> getWeblogUsers(Weblog weblog, boolean enabledOnly) throws WebloggerException {
        List<User> users = new ArrayList<>();
        List<WeblogPermission> perms = weblogPermissionManager.getWeblogPermissions(weblog);
        for (WeblogPermission perm : perms) {
            User user = userManager.getUserByUserName(perm.getUserName());
            if (user == null) {
                LOG.warning("ERROR user is null, userName:" + perm.getUserName());
                continue;
            }
            if (!enabledOnly || user.getIsEnabled()) {
                users.add(user);
            }
        }
        return users;
    }

    

    public Map<String, Long> getWeblogHandleLetterMap() throws WebloggerException {
        String lc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Map<String, Long> results = new TreeMap<String, Long>();
        TypedQuery<Long> query = getNamedQuery(
                "Weblog.getCountByHandleLike", Long.class);
        for (int i = 0; i < 26; i++) {
            char currentChar = lc.charAt(i);
            query.setParameter(1, currentChar + "%");
            List row = query.getResultList();
            Long count = (Long) row.get(0);
            results.put(String.valueOf(currentChar), count);
        }
        return results;
    }

    public List<Weblog> getWeblogsByLetter(char letter, int offset, int length)
            throws WebloggerException {
        TypedQuery<Weblog> query = getNamedQuery(
                "Weblog.getByLetterOrderByHandle", Weblog.class);
        query.setParameter(1, letter + "%");
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        return query.getResultList();
    }

    public List<StatCount> getMostCommentedWeblogs(Date vStartDate, Date vEndDate,
            int offset, int length)
            throws WebloggerException {

        Query query;
        
        Date startDate = vStartDate;
        Date endDate;

        if (vEndDate != null) {
            endDate = vEndDate;
        }else{
            endDate = new Date();
        }

        if (startDate != null) {
            Timestamp start = new Timestamp(startDate.getTime());
            Timestamp end = new Timestamp(endDate.getTime());
            query = em.createNamedQuery(
                    "WeblogEntryComment.getMostCommentedWebsiteByEndDate&StartDate");
            query.setParameter(1, end);
            query.setParameter(2, start);
        } else {
            Timestamp end = new Timestamp(endDate.getTime());
            query = em.createNamedQuery(
                    "WeblogEntryComment.getMostCommentedWebsiteByEndDate");
            query.setParameter(1, end);
        }
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        List queryResults = query.getResultList();
        List<StatCount> results = new ArrayList<StatCount>();
        if (queryResults != null) {
            for (Object obj : queryResults) {
                Object[] row = (Object[]) obj;
                StatCount sc = new StatCount(
                        (String) row[1], // weblog id
                        (String) row[2], // weblog handle
                        (String) row[3], // weblog name
                        "statCount.weblogCommentCountType", // stat type
                        ((Long) row[0]));        // # comments
                sc.setWeblogHandle((String) row[2]);
                results.add(sc);
            }
        }
        
        // Original query ordered by desc # comments.
        // JPA QL doesn't allow queries to be ordered by aggregates; do it in memory
        Collections.sort(results, Collections.reverseOrder(StatCountCountComparator.getInstance()));

        return results;
    }

    /**
     * Get count of weblogs, active and inactive
     */
    public long getWeblogCount() throws WebloggerException {
        List<Long> results = getNamedQuery(
                "Weblog.getCountAllDistinct", Long.class).getResultList();
        return results.get(0);
    }

    //needs some refactoring, but WeblogPermissionManager cannot have
    //WeblogManager in it for circular logic reasons
    public boolean checkPermission(WeblogPermission perm, User user) throws WebloggerException {

        Weblog weblog = getWeblogByHandle(perm.getObjectId());
        WeblogPermission existingPerm = weblogPermissionManager.getWeblogPermission(weblog, user);
        if (existingPerm != null && existingPerm.implies(perm)) {
            return true;
        }

        // if Blog Server admin would still have weblog permission above
        GlobalPermission globalPerm = new GlobalPermission();
        if (globalPerm.implies(perm)) {
            return true;
        }

        LOG.warning("PERM CHECK FAILED: user " + user.getUserName() + " does not have " + perm.toString());

        return false;
    }

}
