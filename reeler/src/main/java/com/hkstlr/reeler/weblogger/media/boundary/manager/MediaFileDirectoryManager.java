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

    @PersistenceContext
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
