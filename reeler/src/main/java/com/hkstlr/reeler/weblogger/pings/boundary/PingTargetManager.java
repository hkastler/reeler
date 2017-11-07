/*
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
 * 
 */
package com.hkstlr.reeler.weblogger.pings.boundary;



import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 *
 * @author henry.kastler
 */
@Stateless
public class PingTargetManager extends AbstractManager<PingTarget> {

    private static final Logger log = Logger.getLogger(PingTargetManager.class.getName());
      

    @PersistenceContext
    public EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PingTargetManager() {
        super(PingTarget.class);
    }

    public void removePingTarget(PingTarget pingTarget) 
            throws WebloggerException {
        // remove contents and then target
        this.removePingTargetContents(pingTarget);
        getEntityManager().remove(pingTarget);
    }

    /**
     * Convenience method which removes any queued pings or auto pings that
     * reference the given ping target.
     */
    private void removePingTargetContents(PingTarget ping) 
            throws WebloggerException {
        // Remove the website's ping queue entries
        Query q = em.createNamedQuery("PingQueueEntry.removeByPingTarget");
        q.setParameter(1, ping);
        q.executeUpdate();
        
        // Remove the website's auto ping configurations
        q = em.createNamedQuery("AutoPing.removeByPingTarget");
        q.setParameter(1, ping);
        q.executeUpdate();
    }

    public void savePingTarget(PingTarget pingTarget)
            throws WebloggerException {
    	getEntityManager().persist(pingTarget);
    }

    public PingTarget getPingTarget(String id)
            throws WebloggerException {
        return (PingTarget)getEntityManager().find(PingTarget.class, id);
    }

    public boolean targetNameExists(String pingTargetName)
            throws WebloggerException {

        // Within that set of targets, fail if there is a target
        // with the same name and that target doesn't
        // have the same id.
        for (PingTarget pt : getCommonPingTargets()) {
            if (pt.getName().equals(pingTargetName)) {
                return true;
            }
        }
        // No conflict found
        return false;
    }

    
    public boolean isUrlWellFormed(String url)
            throws WebloggerException {

        if (url == null || url.trim().length() == 0) {
            return false;
        }
        try {
            URL parsedUrl = new URL(url);
            // OK.  If we get here, it parses ok.  Now just check 
            // that the protocol is http and there is a host portion.
            boolean isHttp = "http".equals(parsedUrl.getProtocol());
            boolean hasHost = (parsedUrl.getHost() != null) && 
                (parsedUrl.getHost().trim().length() > 0);
            return isHttp && hasHost;
        } catch (MalformedURLException e) {
            log.log(Level.WARNING,null,e);
            return false;
        }
    }

    
    public boolean isHostnameKnown(String url)
            throws WebloggerException, URISyntaxException {
        if (url == null || url.trim().length() == 0) {
            return false;
        }
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            if (host == null || host.trim().length() == 0) {
                return false;
            }
           //another test
           new URI(url).parseServerAuthority();            
           return true;
        } catch (MalformedURLException e) {
            log.log(Level.WARNING,null,e);
            return false;
        }
    }

    public List<PingTarget> getCommonPingTargets()
            throws WebloggerException {
        TypedQuery<PingTarget> q = em.createNamedQuery(
                "PingTarget.getPingTargetsOrderByName", PingTarget.class);
        return q.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    
    
}
