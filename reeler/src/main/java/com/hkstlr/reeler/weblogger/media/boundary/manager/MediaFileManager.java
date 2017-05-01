/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.media.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.media.entities.MediaFile;
import com.hkstlr.reeler.weblogger.media.entities.MediaFileDirectory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class MediaFileManager extends AbstractManager<MediaFile> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MediaFileManager() {
        super(MediaFile.class);
    }

    public MediaFileDirectory getDefaultMediaFileDirectory(Weblog weblog) {
        Query query = em.createQuery("SELECT r FROM MediaFileDirectory r WHERE r.weblog = :weblog");
        query.setParameter("weblog", weblog);
        return (MediaFileDirectory) query.getResultList().get(0);
    }

    public void removeAllFiles(Weblog weblog) {
        // TODO Auto-generated method stub

    }

    public void createDefaultMediaFileDirectory(Weblog newWeblog) {
        // TODO Auto-generated method stub

    }

    public MediaFile getMediaFileByOriginalPath(Weblog weblog, String string) {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeMediaFile(Weblog weblog, MediaFile oldmf) {
        // TODO Auto-generated method stub

    }

    public void createMediaFile(Weblog weblog, MediaFile mf) {
        // TODO Auto-generated method stub

    }

}
