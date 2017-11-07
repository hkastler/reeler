/*
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
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryTagManager extends AbstractManager<WeblogEntryTag> {

    private static final Logger log = Logger.getLogger(WeblogEntryTagManager.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    public WeblogEntryTagManager() {
        super(WeblogEntryTag.class);
    }    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryTag findByName(String name) {
        TypedQuery<WeblogEntryTag> q = getNamedQuery("WeblogEntryTag.findByName");
        q.setParameter("name", name);
        WeblogEntryTag tag;
        try {
            tag = q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,"WeblogEntryTag.findByName",e);
            tag = new WeblogEntryTag();
        }
        return tag;
    }

    void removeWeblogEntryTag(WeblogEntryTag tag) {
        remove(tag);
    }

    public WeblogEntryTag findByNameAndWeblogEntry(String name, WeblogEntry weblogEntry) {
        TypedQuery<WeblogEntryTag> q = getNamedQuery("WeblogEntryTag.findByNameAndWeblogEntry");
        q.setParameter("name", name);
        q.setParameter("weblogEntry", weblogEntry);
        WeblogEntryTag tag;
        try {
            tag = q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,"WeblogEntryTag.findByNameAndWeblogEntry",e);
            tag = null;
        }
        return tag;
    }

}
