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
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogCategoryManager extends AbstractManager<WeblogCategory> {

    @PersistenceContext
    protected EntityManager em;
    
    
    public WeblogCategoryManager() {
        super(WeblogCategory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public List<WeblogCategory> getWeblogCategoriesForWeblog(Weblog weblog){        
        Query q = em.createNamedQuery("WeblogCategory.getByWeblog");
        q.setParameter(1, weblog);
        return q.getResultList();
    }
    
}
