/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.AppConstants;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.CommentSearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.control.TagStat;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryAttribute;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.ejb.Stateless;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryManager extends AbstractManager<WeblogEntry> {

    @Inject
    private Logger log;

    @Inject
    private WeblogEntryCommentManager wecm;

    @Inject
    private WeblogEntryTagManager wetm;

    @Inject
    private WeblogEntryTagAggregateManager wetam;

    @Inject
    private WeblogManager weblogManager;

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryManager() {
        super(WeblogEntry.class);
    }

    public void removeWeblogEntry(WeblogEntry entry) throws WebloggerException {
        Weblog weblog = entry.getWebsite();

        CommentSearchCriteria csc = new CommentSearchCriteria();
        csc.setEntry(entry);

        // remove comments
        List<WeblogEntryComment> comments = wecm.getComments(csc);
        for (WeblogEntryComment comment : comments) {
            wecm.remove(comment);
        }

        // remove tag & tag aggregates
        if (entry.getTags() != null) {
            for (WeblogEntryTag tag : entry.getTags()) {
                wetm.removeWeblogEntryTag(tag);
            }
        }

        // remove attributes
        if (entry.getEntryAttributes() != null) {
            for (Iterator it = entry.getEntryAttributes().iterator(); it.hasNext();) {
                WeblogEntryAttribute att = (WeblogEntryAttribute) it.next();
                it.remove();
                this.em.remove(att);
            }
        }

        // remove entry
        this.em.remove(entry);

        // update weblog last modified date.  date updated by saveWebsite()
        if (entry.isPublishEntry()) {
            weblogManager.saveWeblog(weblog);
        }

        // remove entry from cache mapping
        //this.entryAnchorToIdMap.remove(entry.getWebsite().getHandle()+":"+entry.getAnchor());
    }

    public List<TagStat> getTags(Weblog weblog, Object object, Object object2, int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }

    public WeblogEntry getWeblogEntryByHandleAndAnchor(String handle, String anchor) {

        Weblog weblog = null;
        WeblogEntry weblogEntry = null;

        Query website = em.createNamedQuery("Weblog.findByHandle");
        website.setParameter("handle", handle);

        try {
            weblog = (Weblog) website.getSingleResult();
        } catch (Exception e) {

        }

        log.info("weblog: " + weblog.getHandle());

        Query query = em.createNamedQuery("WeblogEntry.findByWebsiteAndAnchor");
        query.setParameter("website", weblog);
        query.setParameter("anchor", anchor);

        try {
            weblogEntry = (WeblogEntry) query.getSingleResult();
        } catch (Exception e) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setText("not found");
            log.log(Level.WARNING, "weblog: {0} not found, reason: {1}", new Object[]{anchor, e.getMessage()});
        }

        log.log(Level.INFO, "weblogEntry found:" + weblogEntry.getAnchor());

        return weblogEntry;
    }

    public List<WeblogEntry> getWeblogEntriesForWeblog(Weblog weblog) {
        Query query = em.createNamedQuery("WeblogEntry.getByWebsite");
        query.setParameter(1, weblog);
        List<WeblogEntry> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public List<Calendar> getWeblogEntryDatesForCalendar(String dateString, Weblog weblog) {

        //dateString expected format = YYYYMM
        log.fine("dateString:" + dateString);
        Integer year = Integer.parseInt(dateString.substring(0, 4));
        log.info("year: " + year);
        Integer month = Integer.parseInt(dateString.substring(4, 6));

        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month - 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month - 1, startDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        Query query = em.createQuery("SELECT DISTINCT we.pubTime FROM WeblogEntry we"
                + " WHERE we.website = :blog"
                + " AND we.publishEntry = true"
                + " AND we.pubTime BETWEEN "
                + ":startDate"
                + " AND "
                + ":endDate");
        query.setParameter("blog", weblog);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Calendar> results = query.getResultList();

        return results;

    }

    public List<WeblogEntry> findByPinnedToMain() {
        Query query = em.createNamedQuery("WeblogEntry.findByPinnedToMain");
        query.setParameter("pinnedToMain", true);
        List<WeblogEntry> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public WeblogEntry getWeblogEntry(String id) {
        WeblogEntry entry = em.find(WeblogEntry.class, id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        return entry;
    }

    public WeblogEntry findForEdit(String id) {
        WeblogEntry entry = findById(id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        entry.getTags().size();
        return entry;
    }

    public List<WeblogEntry> getWeblogEntriesByCategoryName(String weblogCategoryName) {

        List<WeblogEntry> categoryEntries = new ArrayList<>();
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByWeblogCategoryName");
        log.log(Level.FINE, "weblogCategoryName:" + weblogCategoryName);
        String trimmed = weblogCategoryName.trim();
        q.setParameter("weblogCategoryName", trimmed);
        categoryEntries = q.getResultList();
        return categoryEntries;
    }

    public List<WeblogEntry> getWeblogEntriesByCategoryNameAndWeblog(String weblogCategoryName, Weblog weblog) {

        List<WeblogEntry> categoryEntries = new ArrayList<>();
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByCategoryNameAndWeblog");
        log.log(Level.FINE, "weblogCategoryName:" + weblogCategoryName);
        String trimmed = weblogCategoryName.trim();
        q.setParameter("weblogCategoryName", trimmed);
        q.setParameter("weblog", weblog);
        categoryEntries = q.getResultList();
        return categoryEntries;
    }

    public List<WeblogEntry> getWeblogEntriesByDateAndWeblog(String dateString, Weblog weblog) {
        log.fine("dateString:" + dateString);
        Integer year = Integer.parseInt(dateString.substring(0, 4));
        log.info("year: " + year);
        Integer month = Integer.parseInt(dateString.substring(4, 6));
        log.info("month: " + month);
        Integer date = Integer.parseInt(dateString.substring(6, 8));
        log.info("date: " + date);
        Calendar pubTimeBefore = Calendar.getInstance(new Locale(weblog.getLocale()));
        pubTimeBefore.set(year, month - 1, date - 1);
        log.info("pubTimeBefore" + pubTimeBefore.getTime().toString());
        Calendar pubTimeAfter = Calendar.getInstance(new Locale(weblog.getLocale()));
        pubTimeAfter.set(year, month - 1, date + 1);
        log.info("pubTimeAfter" + pubTimeAfter.getTime().toString());
        List<WeblogEntry> entries = new ArrayList<>();
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getWeblogEntriesByDateAndWeblog");
        q.setParameter("pubTimeBefore", pubTimeBefore);
        q.setParameter("pubTimeAfter", pubTimeAfter);
        q.setParameter("weblog", weblog);
        entries = q.getResultList();
        return entries;
    }

    public WeblogEntry getLatestEntryForWeblog(Weblog weblog) {

        Query q = em.createNamedQuery("WeblogEntry.getLatestEntryForWeblog")
                .setParameter("weblog", weblog)
                .setMaxResults(1);
        WeblogEntry we = (WeblogEntry) q.getSingleResult();
        return we;
    }

    /**
     * @inheritDoc
     */
    // TODO: perhaps the createAnchor() and queuePings() items should go outside this method?
    public void saveWeblogEntry(WeblogEntry entry) throws WebloggerException {

        // Entry is invalid without local. if missing use weblog default
        if (entry.getLocale() == null) {
            entry.setLocale(entry.getWebsite().getLocale());
        }

        if (entry.getAnchor() == null || entry.getAnchor().trim().equals("")) {
            //entry.setAnchor(this.createAnchor(entry));
        }       

        // if the entry was published to future, set status as SCHEDULED
        // we only consider an entry future published if it is scheduled
        // more than 1 minute into the future
        if (WeblogEntry.PubStatus.PUBLISHED.equals(entry.getStatus())
                && entry.getPubTime().after(new Date(System.currentTimeMillis() + AppConstants.MIN_IN_MS))) {
            entry.setStatus(WeblogEntry.PubStatus.SCHEDULED.toString());
        }
        
        // Store value object (creates new or updates existing)
        entry.setUpdateTime(new Date());
        save(entry);
        log.info("tags:" + entry.getTags().size());
        
         if (entry.isPublishEntry()) {
            weblogManager.saveWeblog(entry.getWebsite());
        }
        em.flush();
        
    }

}
