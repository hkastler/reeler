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
package com.hkstlr.reeler.planet.boundary.manager;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.planet.entities.Planet;


/**
 *
 * @author henry.kastler
 */

@Stateless
public class PlanetManager extends AbstractManager<Planet>{
	
    @Inject
    private Logger log;
    
    @PersistenceContext
    private EntityManager em;
    
    public PlanetManager(){
        super(Planet.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    
    
}
