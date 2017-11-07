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
package com.hkstlr.reeler.weblogger.pings.boundary;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.pings.entities.AutoPing;
import com.hkstlr.reeler.weblogger.pings.entities.PingQueueEntry;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 *
 * @author henry.kastler
 */
@Stateless
public class PingQueueEntryManager extends AbstractManager<PingQueueEntry> {

    @Inject
    private Logger log;
    
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PingQueueEntryManager() {
        super(PingQueueEntry.class);
    }
    
    // private helper to determine if an has already been queued 
    // for the same website and ping target.
    private boolean isAlreadyQueued(AutoPing autoPing){
        // first, determine if an entry already exists
        TypedQuery<PingQueueEntry> q = em.createNamedQuery("PingQueueEntry.getByPingTarget&Website",
                PingQueueEntry.class);
        q.setParameter(1, autoPing.getPingTarget());
        q.setParameter(2, autoPing.getWebsite());
        return !q.getResultList().isEmpty();
    }
    
    public PingQueueEntry getQueueEntry(String id) 
            throws WebloggerException {
        return (PingQueueEntry)em.find(
            PingQueueEntry.class, id);
    }

    public void saveQueueEntry(PingQueueEntry pingQueueEntry) 
            throws WebloggerException {
        log.warning("Storing ping queue entry: " + pingQueueEntry);
        save(pingQueueEntry);
    }

    public void removeQueueEntry(PingQueueEntry pingQueueEntry) 
            throws WebloggerException {
        log.warning("Removing ping queue entry: " + pingQueueEntry);
        em.remove(pingQueueEntry);
    }

    
    public void addQueueEntry(AutoPing autoPing, TimeZone timeZone, Locale locale) throws WebloggerException {
        log.warning("Creating new ping queue entry for auto ping configuration: " 
            + autoPing);
        
        // First check if there is an existing ping queue entry 
        // for the same target and website
        if (isAlreadyQueued(autoPing)) {
            log.warning("A ping queue entry is already present" +
                " for this ping target and website: " + autoPing);
            return;
        }

        Calendar now = Calendar.getInstance(timeZone, locale);
        now.setTime(new Date());
        PingQueueEntry pingQueueEntry =
                new PingQueueEntry(now, autoPing.getPingTarget(), 
                    autoPing.getWebsite(), 0);
        this.saveQueueEntry(pingQueueEntry);
    }

    public List<PingQueueEntry> getAllQueueEntries()
            throws WebloggerException {
        return getNamedQuery("PingQueueEntry.getAllOrderByEntryTime",
                PingQueueEntry.class).getResultList();
    }

    
    
}
