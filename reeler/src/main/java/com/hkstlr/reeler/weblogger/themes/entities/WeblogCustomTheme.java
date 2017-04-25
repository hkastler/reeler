package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.media.boundary.manager.MediaFileManager;
import com.hkstlr.reeler.weblogger.media.entities.MediaFile;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import javax.ejb.EJB;

public class WeblogCustomTheme extends WeblogTheme {

    private static final long serialVersionUID = 1L;

    private transient Logger log = Logger.getLogger(WeblogCustomTheme.class.getName());
    
    @EJB
    private transient WeblogManager weblogManager;
    
    @EJB
    private transient MediaFileManager mediaFileManager;

    public WeblogCustomTheme(Weblog weblog) {
        super(weblog);
    }

    @Override
    public String getId() {
        return CUSTOM;
    }

    @Override
    public String getName() {
        return CUSTOM;
    }

    public String getType() {
        return CUSTOM;
    }

    @Override
    public String getDescription() {
        return CUSTOM;
    }

    @Override
    public String getAuthor() {
        return "N/A";
    }

    @Override
    public Date getLastModified() {
        return this.weblog.getLastModified();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int compareTo(Theme other) {
        return getName().compareTo(other.getName());
    }

    /**
     * Get the collection of all templates associated with this Theme.
     *
     * @return 
     * @throws WebloggerException
     */
    public List<? extends ThemeTemplate> getTemplates() throws WebloggerException {
        return weblogManager.getTemplates(this.weblog);
    }

    /**
     * Lookup the stylesheet template for this theme. Returns null if no
     * stylesheet can be found.
     */
    public ThemeTemplate getStylesheet() throws WebloggerException {
        return getTemplateByAction(ComponentType.STYLESHEET);
    }

    /**
     * Lookup the default template. Returns null if the template cannot be
     * found.
     * @return 
     */
    @Override
    public ThemeTemplate getDefaultTemplate() {
        ThemeTemplate themeTemplate = null;
        try {
            themeTemplate = weblogManager.getTemplateByAction(this.weblog, ComponentType.WEBLOG);
        } catch (WebloggerException ex) {
            Logger.getLogger(WeblogCustomTheme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return themeTemplate;
    }

    /**
     * Lookup the specified template by action. Returns null if the template
     * cannot be found.
     * @param action
     * @return 
     */
    @Override
    public ThemeTemplate getTemplateByAction(ComponentType action) {
        if (action == null) {
            return null;
        }
        try {
            return weblogManager.getTemplateByAction(this.weblog, action);
        } catch (WebloggerException ex) {
            Logger.getLogger(WeblogCustomTheme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Lookup the specified template by name. Returns null if the template
     * cannot be found.
     *
     * @param name
     * @return 
     * @throws WebloggerException
     */
    @Override
    public ThemeTemplate getTemplateByName(String name) throws WebloggerException {
        if (name == null) {
            return null;
        }
        ThemeTemplate tt;
        tt = weblogManager.getTemplateByName(this.weblog, name);
        return tt;
    }

    /**
     * Lookup the specified template by link. Returns null if the template
     * cannot be found.
     * @param link
     * @return 
     */
    @Override
    public ThemeTemplate getTemplateByLink(String link){
        if (link == null) {
            return null;
        }
        try {
            return weblogManager.getTemplateByLink(this.weblog, link);
        } catch (WebloggerException ex) {
            Logger.getLogger(WeblogCustomTheme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Lookup the specified resource by path. Returns null if the resource
     * cannot be found.
     * @param path
     * @return 
     * @throws com.hkstlr.reeler.app.control.WebloggerException
     */
    @Override
    public ThemeResource getResource(String path){
        ThemeResource resource;
        MediaFile mf = mediaFileManager.getMediaFileByOriginalPath(this.weblog, path);
        File file = new File(mf.getOriginalPath());
        resource = new ThemeResource(path,file);
       
        return resource;
    }

    @Override
    public List<ThemeResource> getResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ThemeResource getPreviewImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    
    public int compareTo(WeblogCustomTheme other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof WeblogCustomTheme)) {
            return false;
        }
        WeblogCustomTheme other = (WeblogCustomTheme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        WeblogCustomTheme wct = (WeblogCustomTheme)o;
        return (this.getId().compareTo(wct.getId()));
    }

   

}
