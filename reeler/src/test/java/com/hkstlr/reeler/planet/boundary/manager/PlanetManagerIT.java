package com.hkstlr.reeler.planet.boundary.manager;

import static org.junit.Assert.*;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.boundary.manager.Manager;
import com.hkstlr.reeler.app.control.LoggerExposer;
import com.hkstlr.reeler.app.entities.AbstractEntity;

import com.hkstlr.reeler.planet.entities.Planet;
import com.hkstlr.reeler.planet.entities.PlanetGroup;


import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Ignore;


@RunWith(Arquillian.class)
public class PlanetManagerIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "planet-manager-test.war")
                .addClasses(PlanetManager.class, PlanetGroup.class, Planet.class,
                        Manager.class, AbstractManager.class, AbstractEntity.class,
                        LoggerExposer.class)
                .addAsWebInfResource("test-persistence-web.xml", "web.xml")
                .addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    PlanetManager cut;
    
    Logger log = Logger.getLogger(PlanetManagerIT.class.getName());

    @Test
    @InSequence(1)
    public void testCreate() {
    	log.log(Level.INFO, "*************************");
    	log.log(Level.INFO, "testCreate");
    	
        // Save a new planet.        
        Planet planet = new Planet();
        planet.setTitle("title");
        planet.setHandle("handle");
        log.log(Level.INFO, "id:{0}", planet.getId());
        
        cut.save(planet);
        log.log(Level.INFO,"planet {0} saved",planet.getId());
        
        // Make sure it was correctly saved.
        try {
            planet = cut.findById(planet.getId());
            log.log(Level.INFO, "findingById:{0} success", planet.getId());
        } catch (Exception e) {
            System.out.println("error findingById:" + planet.getId());
        }
        
        /*
         try {
            planet = cut.find(planet);
        } catch (Exception e) {
        	//known bug on char based id field?
            //log.log(Level.INFO,"error find:" + planet.getId(), e);
        }
		*/
        assertEquals("title", planet.getTitle());

    }
    
    @Test
    @InSequence(2)
    public void testDelete() {
    	log.log(Level.INFO, "*************************");
    	log.log(Level.INFO, "testDelete");
    	// Save a new planet.        
        Planet planet = new Planet();
        planet.setTitle("title");
        //TODO: test for unique
        //TODO: test for length
        planet.setHandle(UUID.randomUUID().toString().substring(0, 32));
        //log.log(Level.ALL, "id:{0}", planet.getId());
        
        cut.save(planet);
        log.log(Level.INFO,"planet {0} saved",planet.getId());
        
        // Make sure it was correctly saved.
        try {
            planet = cut.findById(planet.getId());
            log.log(Level.INFO, "findingById:{0} success", planet.getId());
        } catch (Exception e) {
            System.out.println("error findingById:" + planet.getId());
        }
        
        cut.remove(planet);
        
        // Make sure it was correctly removed.
        try {
            planet = cut.findById(planet.getId());
            
        } catch (Exception e) {
            planet = null;
            log.log(Level.INFO, "deleted", e);
        }
        
        assertNull(planet);

    }
    
}