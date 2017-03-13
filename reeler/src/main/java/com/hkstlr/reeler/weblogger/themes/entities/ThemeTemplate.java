package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hkstlr.reeler.weblogger.themes.entities.TemplateRendition;
import com.hkstlr.reeler.weblogger.themes.control.RenditionType;

import com.hkstlr.reeler.weblogger.themes.control.ComponentType;

public abstract class ThemeTemplate {
	
	
	private String id = null;
    private ComponentType action = null;
    private String name = null;
    private String description = null;
    private String contents = null;
    private String link = null;
    private Date lastModified = null;
    private boolean hidden = false;
    private boolean navbar = false;
    private String  outputContentType = null;
    private String type = null;

    //hash map to cache template Code objects parsed
    private Map<RenditionType, TemplateRendition> templateRenditionHashMap = new HashMap<RenditionType, TemplateRendition>();
    
    public ThemeTemplate() {}
    
    public ThemeTemplate(String id, ComponentType action, String name,
            String desc, String contents, String link, Date date, 
            boolean hidden, boolean navbar) {
        
        this.id = id;
        this.action = action;
        this.name = name;
        this.description = desc;
        this.contents = contents;
        this.link = link;
        this.lastModified = date;
        this.hidden = hidden;
        this.navbar = navbar;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean isHidden) {
        this.hidden = isHidden;
    }

    public boolean isNavbar() {
        return navbar;
    }

    public void setNavbar(boolean navbar) {
        this.navbar = navbar;
    }

    public String getOutputContentType() {
        return outputContentType;
    }

    public void setOutputContentType(String outputContentType) {
        this.outputContentType = outputContentType;
    }
    
    public String toString() {
        return (id + "," + name + "," + description + "," + link + "," + 
                lastModified + "\n\n" + contents + "\n");
    }

    public ComponentType getAction() {
        return action;
    }

    public void setAction(ComponentType action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public TemplateRendition getTemplateRendition(RenditionType type){
        return templateRenditionHashMap.get(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addTemplateRendition(CustomTemplateRendition customTemplateRendition){
        //this.templateRenditionHashMap.put(customTemplateRendition.getType(), customTemplateRendition);
    }

	

}
