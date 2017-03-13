/*
 * Author henry.kastler hkstlr.com 2017 
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
 */
package com.hkstlr.reeler.app.boundary.manager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.hkstlr.reeler.weblogger.control.StringChanger;
import javax.ejb.Stateless;

/**
 *
 * @author henry.kastler
 */

public abstract class AbstractManager<T> {
	
	@Inject
	private Logger log;
	
    private Class<T> entityClass;
    
    protected AbstractManager(){}

    public AbstractManager(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    public void create(T entity) {
        persist(entity);
    }
    
    public void save(T entity) {
    	
        persist(entity);
    }
    
    public void persist(T entity) {
    	getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public T findById(String id){
       String className = entityClass.getSimpleName();
       log.log(Level.INFO, "className:" + className);
       String queryName = className.concat(".findById");
       Query query = getEntityManager().createNamedQuery(queryName);
       query.setParameter("id", id);
       T record = null;
       try{
    	    record = (T) query.getSingleResult();
       } catch (NoResultException e) {
       		log.log(Level.INFO, className + ".findById:" + id + e.toString());
           
       }
       return record;
    }
    
    public T findByField(String fieldName, Object fieldValue){
        String className = entityClass.getSimpleName();
        log.log(Level.INFO, "className:" + className);
        String queryNameSuffix = StringChanger.toTitleCase(fieldName);
        String queryName = className.concat(".findBy").concat(queryNameSuffix);
        log.log(Level.INFO, queryName + ":" + fieldValue.toString());
        Query query = getEntityManager().createNamedQuery(queryName);
        query.setParameter(fieldName, fieldValue);
        T record = null;
        try{
     	    record = (T) query.getSingleResult();
        } catch (NoResultException e) {
        		log.log(Level.INFO, queryName + ":" + e.toString());
            
        }
        return record;
     }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public <T> TypedQuery<T> getNamedQuery(String queryName) {
        TypedQuery<T> q = (TypedQuery<T>) getEntityManager().createNamedQuery(queryName, this.entityClass);
        // For performance, never flush/commit prior to running queries.
        // Roller code assumes this behavior
        
        return q;
    }  
    

    //from roller
    public <T> TypedQuery<T> getNamedQuery(String queryName, Class<T> resultClass) {
        TypedQuery<T> q = getEntityManager().createNamedQuery(queryName, resultClass);
        // For performance, never flush/commit prior to running queries.
        // Roller code assumes this behavior
        
        return q;
    }    
    /**
     * Get named query with default flush mode (usually FlushModeType.AUTO)
     * FlushModeType.AUTO commits changes to DB prior to running statement
     *
     * @param queryName   the name of the query
     * @param resultClass return type of query
     */
    public <T> TypedQuery<T> getNamedQueryCommitFirst(String queryName, Class<T> resultClass) {
        EntityManager em = getEntityManager();
        return em.createNamedQuery(queryName, resultClass);
    }
    
}
