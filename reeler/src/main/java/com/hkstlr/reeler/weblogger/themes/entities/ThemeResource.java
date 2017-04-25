/*
 * originally from org.apache.roller.weblogger.pojos.ThemeResource;
*/

package com.hkstlr.reeler.weblogger.themes.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import com.hkstlr.reeler.app.entities.Resource;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThemeResource extends Resource implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1173782741562126716L;

    // the physical java.io.File backing this resource
    private File resourceFile = null;

    // the relative path of the resource within the theme
    private String relativePath = null;

    public ThemeResource(String path, File file) {
        super();
        relativePath = path;
        resourceFile = file;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(ThemeResource other) {
        return getPath().compareTo(other.getPath());
    }

    public ThemeResource[] getChildren() {
        return null;
    }

    @Override
    public String getName() {
        return resourceFile.getName();
    }

    @Override
    public String getPath() {
        return relativePath;
    }

    @Override
    public Long getLastModified() {
        return resourceFile.lastModified();
    }

    @Override
    public Long getLength() {
        return resourceFile.length();
    }

    public boolean isDirectory() {
        return resourceFile.isDirectory();
    }

    public boolean isFile() {
        return resourceFile.isFile();
    }

    public InputStream getInputStream() {
        try {
            return new FileInputStream(resourceFile);
        }catch (java.io.FileNotFoundException e){
            Logger.getLogger(ThemeResource.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("error getting resourceFile", e);    
         }
    }

}
