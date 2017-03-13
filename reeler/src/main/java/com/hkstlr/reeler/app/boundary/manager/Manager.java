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

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
public interface Manager<T> {

    int count();

    void create(T entity);
    
    void save(T entity);
    
    void persist(T entity);

    void edit(T entity);   

    void remove(T entity);        

    T find(Object id);

    List<T> findAll();

    T findById(String id);

    List<T> findRange(int[] range);

    //from roller
    <T> TypedQuery<T> getNamedQuery(String queryName, Class<T> resultClass);

    <T> TypedQuery<T> getNamedQueryCommitFirst(String queryName, Class<T> resultClass);

    
    
}
