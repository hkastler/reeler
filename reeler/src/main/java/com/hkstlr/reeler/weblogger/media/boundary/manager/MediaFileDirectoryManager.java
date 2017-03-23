/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.media.boundary.manager;



import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.media.entities.MediaFileDirectory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author henry.kastler
 */

public class MediaFileDirectoryManager extends AbstractManager<MediaFileDirectory> {

    @PersistenceContext(unitName = "rollerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MediaFileDirectoryManager() {
        super(MediaFileDirectory.class);
    }

	public MediaFileDirectory getMediaFileDirectoryByName(Weblog weblog, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public MediaFileDirectory createMediaFileDirectory(Weblog weblog, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public MediaFileDirectory getDefaultMediaFileDirectory(Weblog weblog) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
