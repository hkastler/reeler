package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.hkstlr.reeler.app.control.WebloggerException;
//import com.hkstlr.reeler.weblogger.boundary.manager.MediaFileManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.media.entities.MediaFile;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;

public class WeblogCustomTheme extends WeblogTheme {

    private static final long serialVersionUID = 1L;

    private transient Logger log = Logger.getLogger(WeblogCustomTheme.class.getName());

    public WeblogCustomTheme(Weblog weblog) {
        super(weblog);
    }

    public String getId() {
        return CUSTOM;
    }

    public String getName() {
        return CUSTOM;
    }

    public String getType() {
        return CUSTOM;
    }

    public String getDescription() {
        return CUSTOM;
    }

    public String getAuthor() {
        return "N/A";
    }

    public Date getLastModified() {
        return this.weblog.getLastModified();
    }

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
     * @throws WebloggerException
     */
    public List<? extends ThemeTemplate> getTemplates() throws WebloggerException {
        return null;//weblogManager.getTemplates(this.weblog);
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
     */
    public ThemeTemplate getDefaultTemplate() {
        ThemeTemplate themeTemplate = null;
        themeTemplate = null;//weblogManager.getTemplateByAction(this.weblog, ComponentType.WEBLOG);
        return themeTemplate;
    }

    /**
     * Lookup the specified template by action. Returns null if the template
     * cannot be found.
     */
    public ThemeTemplate getTemplateByAction(ComponentType action) throws WebloggerException {
        if (action == null) {
            return null;
        }
        return null;// weblogManager.getTemplateByAction(this.weblog, action);
    }

    /**
     * Lookup the specified template by name. Returns null if the template
     * cannot be found.
     *
     * @throws WebloggerException
     */
    public ThemeTemplate getTemplateByName(String name) throws WebloggerException {
        if (name == null) {
            return null;
        }
        ThemeTemplate tt;
        tt = null;//weblogManager.getTemplateByName(this.weblog, name);
        return tt;
    }

    /**
     * Lookup the specified template by link. Returns null if the template
     * cannot be found.
     */
    public ThemeTemplate getTemplateByLink(String link) throws WebloggerException {
        if (link == null) {
            return null;
        }
        return null;//weblogManager.getTemplateByLink(this.weblog, link);
    }

    /**
     * Lookup the specified resource by path. Returns null if the resource
     * cannot be found.
     */
    public ThemeResource getResource(String path) throws WebloggerException {
        ThemeResource resource = null;
        MediaFile mf = null; //mediaFileManager.getMediaFileByOriginalPath(this.weblog, path);
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
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
