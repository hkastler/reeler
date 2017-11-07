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
 * derived from roller code
 */
package com.hkstlr.reeler.weblogger.pings.boundary;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.pings.entities.AutoPing;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class AutoPingManager extends AbstractManager<AutoPing> {

    
    @Inject
    private Logger logger;
    
    
    
    @EJB
    private PingQueueEntryManager pqem;
    
    @PersistenceContext
    private EntityManager em;    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutoPingManager() {
        super(AutoPing.class);
    }

    public void saveAutoPing(AutoPing autoPing) {
        save(autoPing);

    }

     public void removeAutoPing(AutoPing autoPing) throws WebloggerException {
        em.remove(autoPing);
    }

    public void removeAutoPing(PingTarget pingTarget, Weblog website) throws WebloggerException {
        Query q = em.createNamedQuery("AutoPing.removeByPingTarget&Website");
        q.setParameter(1, pingTarget);
        q.setParameter(2, website);
        q.executeUpdate();
    }

    public void removeAutoPings(Collection<AutoPing> autopings) throws WebloggerException {
        removeAll(autopings);
    }

    public void removeAllAutoPings() throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getAll", AutoPing.class);
        removeAutoPings(q.getResultList());
    }

   

    public List<AutoPing> getAutoPingsByWebsite(Weblog website) throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getByWebsite", AutoPing.class);
        q.setParameter(1, website);
        return q.getResultList();
    }

    public List<AutoPing> getAutoPingsByTarget(PingTarget pingTarget) throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getByPingTarget", AutoPing.class);
        q.setParameter(1, pingTarget);
        return q.getResultList();
    }

    public List<AutoPing> getApplicableAutoPings(WeblogEntry changedWeblogEntry) throws WebloggerException {
        return getAutoPingsByWebsite(changedWeblogEntry.getWebsite());
    }

}
