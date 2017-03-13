/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.themes.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.weblogger.themes.control.RenditionType;
import com.hkstlr.reeler.weblogger.themes.control.TemplateLanguage;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "custom_template_rendition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomTemplateRendition.findAll", query = "SELECT c FROM CustomTemplateRendition c")
    , @NamedQuery(name = "CustomTemplateRendition.findById", query = "SELECT c FROM CustomTemplateRendition c WHERE c.id = :id")
    , @NamedQuery(name = "CustomTemplateRendition.findByTemplate", query = "SELECT c FROM CustomTemplateRendition c WHERE c.weblogTemplate = :template")
    , @NamedQuery(name = "CustomTemplateRendition.findByTemplateLanguage", query = "SELECT c FROM CustomTemplateRendition c WHERE c.templateLanguage = :templateLanguage")
    , @NamedQuery(name = "CustomTemplateRendition.findByType", query = "SELECT c FROM CustomTemplateRendition c WHERE c.type = :type")})
public class CustomTemplateRendition extends TemplateRendition implements Serializable  {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "template", nullable = false, length = 2147483647)
    private String weblogTemplate;
    
    @Size(max = 48)
    @Column(name = "templatelang", length = 48)
    private TemplateLanguage templateLanguage;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "type", nullable = false, length = 16)
    private RenditionType type;
    
    @JoinColumn(name = "templateid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeblogCustomTemplate weblogCustomTemplate;

    public CustomTemplateRendition() {
    	
    }
    
    public CustomTemplateRendition(WeblogCustomTemplate template, RenditionType type) {
		this.weblogCustomTemplate = template;
		this.type = type;
		weblogCustomTemplate.addTemplateRendition(this);
	}

    public CustomTemplateRendition(String id) {
        this.id = id;
    }

    public CustomTemplateRendition(String id, String template, RenditionType type) {
        this.id = id;
        this.weblogTemplate = template;
        this.type = type;
    }

    public CustomTemplateRendition(WeblogTemplate template2, RenditionType type2) {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeblogTemplate() {
		return weblogTemplate;
	}

	public void setWeblogTemplate(String weblogTemplate) {
		this.weblogTemplate = weblogTemplate;
	}

	public TemplateLanguage getTemplateLanguage() {
		return templateLanguage;
	}

	public void setTemplateLanguage(TemplateLanguage templateLanguage) {
		this.templateLanguage = templateLanguage;
	}

	public RenditionType getType() {
        return type;
    }

    public void setType(RenditionType type) {
        this.type = type;
    }
    
    public WeblogCustomTemplate getWeblogCustomTemplate() {
		return weblogCustomTemplate;
	}

	public void setWeblogCustomTemplate(WeblogCustomTemplate weblogCustomTemplate) {
		this.weblogCustomTemplate = weblogCustomTemplate;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomTemplateRendition)) {
            return false;
        }
        CustomTemplateRendition other = (CustomTemplateRendition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.CustomTemplateRendition[ id=" + id + " ]";
    }
    
}
