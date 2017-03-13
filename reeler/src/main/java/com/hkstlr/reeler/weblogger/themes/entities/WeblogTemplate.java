package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;
import com.hkstlr.reeler.weblogger.themes.control.RenditionType;

public class WeblogTemplate extends ThemeTemplate {

	 public static final long serialVersionUID = -613737191638263428L;
	    public static final String DEFAULT_PAGE = "Weblog";
	    
	    private static Set<String> requiredTemplates = null;
	    
	    // attributes
	    private String id = UUID.randomUUID().toString();
	    private ComponentType action = null;
	    private String  name = null;
	    private String  description = null;
	    private String  link = null;
	    private Date    lastModified = null;
	    private boolean hidden = false;
	    private boolean navbar = false;
	    private String  outputContentType = null;

	    // associations
	    private Weblog weblog = null;

	    static {
	        requiredTemplates = new HashSet<String>();
	        requiredTemplates.add("Weblog");
	        requiredTemplates.add("_day");
	    }

	    public WeblogTemplate() {}
	    
	    public String getId() {
	        return this.id;
	    }
	    
	    public void setId( String id ) {
	        this.id = id;
	    }

	    public Weblog getWeblog() {
	        return this.weblog;
	    }
	    
	    public void setWeblog( Weblog website ) {
	        this.weblog = website;
	    }
	    
	    public ComponentType getAction() {
	        return action;
	    }

	    public void setAction(ComponentType action) {
	        this.action = action;
	    }

	    public String getName() {
	        return this.name;
	    }
	    
	    public void setName( String name ) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return this.description;
	    }
	    
	    public void setDescription( String description ) {
	        this.description = description;
	    }

	    public String getLink() {
	        return this.link;
	    }
	    
	    public void setLink( String link ) {
	        this.link = link;
	    }

	    public Date getLastModified() {
	        return lastModified;
	    }
	    
	    public void setLastModified(final Date newtime ) {
	        lastModified = newtime;
	    }

	    public boolean isNavbar() {
	        return navbar;
	    }

	    public void setNavbar(boolean navbar) {
	        this.navbar = navbar;
	    }

	    public boolean isHidden() {
	        return hidden;
	    }

	    public void setHidden(boolean isHidden) {
	        this.hidden = isHidden;
	    }

	    /**
	     * Content-type rendered by template or null for auto-detection by link extension.
	     */
	    public String getOutputContentType() {
	        return outputContentType;
	    }
	    
	    public void setOutputContentType(String outputContentType) {
	        this.outputContentType = outputContentType;
	    }

	    private List<CustomTemplateRendition> templateRenditions = new ArrayList<CustomTemplateRendition>();

	    /**
	     * Determine if this WeblogTemplate is required or not.
	     */
	    public boolean isRequired() {
	       /*
	        * this is kind of hacky right now, but it's like that so we can be
	        * reasonably flexible while we migrate old blogs which may have some
	        * pretty strange customizations.
	        *
	        * my main goal starting now is to prevent further deviations from the
	        * standardized templates as we move forward.
	        *
	        * eventually, the required flag should probably be stored in the db
	        * and possibly applicable to any template.
	        */
	        return (requiredTemplates.contains(getName()) || "Weblog".equals(getLink()));
	    }

	    /**
	     * A convenience method for testing if this template represents a 'custom'
	     * template, meaning a template with action = ACTION_CUSTOM.
	     */
	    public boolean isCustom() {
	        return ComponentType.CUSTOM.equals(getAction()) && !isRequired();
	    }


	    public List<CustomTemplateRendition> getTemplateRenditions() {
	        return templateRenditions;
	    }

	    public void setTemplateRenditions(List<CustomTemplateRendition> templateRenditions) {
	        this.templateRenditions = templateRenditions;
	    }

	    public CustomTemplateRendition getTemplateRendition(RenditionType desiredType) {
	        for (CustomTemplateRendition rnd : templateRenditions) {
	            if (rnd.getType().equals(desiredType)) {
	                return rnd;
	            }
	        }
	        return null;
	    }

	    public void addTemplateRendition(CustomTemplateRendition newRendition) {
	        if (hasTemplateRendition(newRendition)) {
	            throw new IllegalArgumentException("Rendition type '" + newRendition.getType()
	                    + " for template '" + this.getName() + "' already exists.");
	        }
	        templateRenditions.add(newRendition);
	    }

	    public boolean hasTemplateRendition(CustomTemplateRendition proposed) {
	        for (CustomTemplateRendition rnd : templateRenditions) {
	            if(rnd.getType().equals(proposed.getType())) {
	                return true;
	            }
	        }
	        return false;
	    }

	    //------------------------------------------------------- Good citizenship

	    public String toString() {
	        return "{" + getId() + ", " + getName() + ", " + getLink() + "}";
	    }

            /*	    public boolean equals(Object other) {
            if (other == this) {
            return true;
            }
            if (!(other instanceof WeblogTemplate)) {
            return false;
            }
            WeblogTemplate o = (WeblogTemplate)other;
            return new EqualsBuilder()
            .append(getName(), o.getName())
            .append(getWeblog(), o.getWeblog())
            .isEquals();
            }
            
            public int hashCode() {
            return new HashCodeBuilder()
            .append(getName())
            .append(getWeblog())
            .toHashCode();
            }*/

}
