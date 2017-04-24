package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.media.entities.MediaFile;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import javax.ejb.EJB;

public class WeblogSharedTheme extends WeblogTheme {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger log;

    private SharedTheme theme = null;

    @EJB
    private WeblogManager weblogManager;
    // @Inject
    // MediaFileManager mediaFileManager;
    public WeblogSharedTheme(Weblog weblog, SharedTheme theme) {
        super(weblog);
        this.theme = theme;
    }

    public String getId() {
        return this.theme.getId();
    }

    public String getName() {
        return this.theme.getName();
    }

    public String getDescription() {
        return this.theme.getDescription();
    }

    public Date getLastModified() {
        return this.theme.getLastModified();
    }

    public boolean isEnabled() {
        return this.theme.isEnabled();
    }

    public int compareTo(Theme other) {
        return theme.compareTo(other);
    }

    /**
     * Get the collection of all templates associated with this Theme.
     */
    public List<ThemeTemplate> getTemplates() throws WebloggerException {

        Map<String, ThemeTemplate> pages = new TreeMap<String, ThemeTemplate>();

        // first get the pages from the db
        /*
        try {
            for (ThemeTemplate template : //weblogManager.getTemplates(this.weblog)) {
                pages.put(template.getName(), template);
            }
        } catch(Exception e) {
            // db error
            log.warning(e.getMessage());
        }
         */
        // now get theme pages if needed and put them in place of db pages
        try {
            for (ThemeTemplate template : this.theme.getTemplates()) {
                // note that this will put theme pages over custom
                // pages in the pages list, which is what we want
                pages.put(template.getName(), template);
            }
        } catch (Exception e) {
            // how??
            log.log(Level.WARNING, null,e);
        }

        return new ArrayList<ThemeTemplate>(pages.values());
    }

    /**
     * Lookup the stylesheet template for this theme. Returns null if no
     * stylesheet can be found.
     */
    public ThemeTemplate getStylesheet() throws WebloggerException {
        // stylesheet is handled differently than other templates because with
        // the stylesheet we want to return the weblog custom version if it
        // exists, otherwise we return the shared theme version

        // load from theme first to see if we even support a stylesheet
        ThemeTemplate stylesheet = this.theme.getStylesheet();
        if (stylesheet != null) {
            // now try getting custom version from weblog
            ThemeTemplate override = weblogManager.getTemplateByAction(this.weblog, ComponentType.STYLESHEET);
            if (override != null) {
                stylesheet = override;
            }
        }
        return stylesheet;
    }

    /**
     * Lookup the default template.
     */
    public ThemeTemplate getDefaultTemplate() throws WebloggerException {
        return this.theme.getDefaultTemplate();
    }

    /**
     * Lookup the specified template by action. Returns null if the template
     * cannot be found.
     */
    public ThemeTemplate getTemplateByAction(ComponentType action) throws WebloggerException {

        if (action == null) {
            return null;
        }

        // NOTE: we specifically do *NOT* return templates by action from the
        // weblog's custom templates if the weblog is using a theme because we
        // don't want old templates to take effect when using a specific theme
        return this.theme.getTemplateByAction(action);
    }

    /**
     * Lookup the specified template by name. Returns null if the template
     * cannot be found.
     */
    public ThemeTemplate getTemplateByName(String name) throws WebloggerException {

        if (name == null) {
            return null;
        }

        ThemeTemplate template;

        // if name refers to the stylesheet then return result of getStylesheet()
        ThemeTemplate stylesheet = getStylesheet();
        if (stylesheet != null && name.equals(stylesheet.getName())) {
            return stylesheet;
        }

        // first check if this user has selected a theme
        // if so then return the proper theme template
        template = this.theme.getTemplateByName(name);

        // if we didn't get the Template from a theme then look in the db
        if (template == null) {
            template = null;//weblogManager.getTemplateByName(this.weblog, name);
        }

        return template;
    }

    /**
     * Lookup the specified template by link. Returns null if the template
     * cannot be found.
     */
    public ThemeTemplate getTemplateByLink(String link) throws WebloggerException {

        if (link == null) {
            return null;
        }

        ThemeTemplate template;

        // if name refers to the stylesheet then return result of getStylesheet()
        ThemeTemplate stylesheet = getStylesheet();
        if (stylesheet != null && link.equals(stylesheet.getLink())) {
            return stylesheet;
        }

        // first check if this user has selected a theme
        // if so then return the proper theme template
        template = this.theme.getTemplateByLink(link);

        // if we didn't get the Template from a theme then look in the db
        if (template == null) {
            template = null;//weblogManager.getTemplateByLink(this.weblog, link);
        }

        return template;
    }

    /**
     * Lookup the specified resource by path. Returns null if the resource
     * cannot be found.
     */
    public ThemeResource getResource(String path) throws WebloggerException {

        if (path == null) {
            return null;
        }

        ThemeResource resource;

        // first check in our shared theme
        resource = this.theme.getResource(path);

        // if we didn't find it in our theme then look in weblog uploads
        if (resource == null) {
            MediaFile mf = null; //mediaFileManager.getMediaFileByOriginalPath(this.weblog, path);            
        }

        return resource;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
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

}
