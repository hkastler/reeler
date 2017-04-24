package com.hkstlr.reeler.weblogger.themes.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;

public abstract class Theme implements Comparable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1335478149482488799L;
    protected String id = null;
    protected String name = null;
    protected String description = null;
    protected String author = null;
    protected Date lastModified = null;
    protected boolean enabled = false;

    public abstract List<ThemeResource> getResources();

    public abstract ThemeResource getPreviewImage();

    /**
     * Get the list of all templates associated with this Theme.
     *
     * @throws WebloggerException
     */
    public abstract List<? extends ThemeTemplate> getTemplates() throws WebloggerException;

    /**
     * Lookup the stylesheet template for the Theme.
     *
     * @throws WebloggerException
     */
    public abstract ThemeTemplate getStylesheet() throws WebloggerException;

    /**
     * Lookup the default template for the Theme.
     *
     * @throws WebloggerException
     */
    public abstract ThemeTemplate getDefaultTemplate() throws WebloggerException;

    /**
     * Lookup a template by action. Returns null if the template cannot be
     * found.
     *
     * @throws WebloggerException
     */
    public abstract ThemeTemplate getTemplateByAction(ComponentType action) throws WebloggerException;

    /**
     * Lookup a template by name. Returns null if the template cannot be found.
     *
     * @throws WebloggerException
     */
    public abstract ThemeTemplate getTemplateByName(String name) throws WebloggerException;

    /**
     * Lookup a template by link. Returns null if the template cannot be found.
     *
     * @throws WebloggerException
     */
    public abstract ThemeTemplate getTemplateByLink(String link) throws WebloggerException;

    /**
     * Lookup a resource by path. Returns null if the resource cannot be found.
     *
     * @throws WebloggerException
     */
    public abstract ThemeResource getResource(String path) throws WebloggerException;
   
    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Theme other) {
        return getName().compareTo(other.getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public WeblogTemplate getTemplateByAction(Weblog weblog, ComponentType action) {
        // TODO Auto-generated method stub
        return null;
    }
}
