/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.themes.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "weblog_custom_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogCustomTemplate.findAll", query = "SELECT w FROM WeblogCustomTemplate w")
    , @NamedQuery(name = "WeblogCustomTemplate.findById", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.id = :id")
    , @NamedQuery(name = "WeblogCustomTemplate.findByName", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.name = :name")
    , @NamedQuery(name = "WeblogCustomTemplate.findByDescription", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.description = :description")
    , @NamedQuery(name = "WeblogCustomTemplate.findByLink", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.link = :link")
    , @NamedQuery(name = "WeblogCustomTemplate.findByUpdatetime", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.updatetime = :updatetime")
    , @NamedQuery(name = "WeblogCustomTemplate.findByHidden", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.hidden = :hidden")
    , @NamedQuery(name = "WeblogCustomTemplate.findByNavbar", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.navbar = :navbar")
    , @NamedQuery(name = "WeblogCustomTemplate.findByOutputtype", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.outputtype = :outputtype")
    , @NamedQuery(name = "WeblogCustomTemplate.findByAction", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.action = :action")
    , @NamedQuery(name = "WeblogTemplate.getByWeblog", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.weblog = ?1")
    , @NamedQuery(name = "WeblogTemplate.getByWeblogOrderByName", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.weblog = ?1 ORDER BY w.name")
    , @NamedQuery(name = "WeblogTemplate.getByWeblog&amp;Link", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.weblog = ?1 AND w.link = ?2")
    , @NamedQuery(name = "WeblogTemplate.getByAction", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.weblog = ?1 AND w.action = ?2")
    , @NamedQuery(name = "WeblogTemplate.getByWeblog&amp;Name", query = "SELECT w FROM WeblogCustomTemplate w WHERE w.weblog = ?1 AND w.name= ?2")})
public class WeblogCustomTemplate extends WeblogTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    
    @Size(max = 255)
    @Column(name = "link", length = 255)
    private String link;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "updatetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "hidden", nullable = false)
    private boolean hidden;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "navbar", nullable = false)
    private boolean navbar;
    
    @Size(max = 48)
    @Column(name = "outputtype", length = 48)
    private String outputtype;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "action", nullable = false, length = 16)
    private ComponentType action;
    
    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Weblog weblog;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weblogCustomTemplate")
    private List<CustomTemplateRendition> weblogCustomTemplates;

    public WeblogCustomTemplate() {
    }

    public WeblogCustomTemplate(String id) {
        this.id = id;
    }

    public WeblogCustomTemplate(String id, String name, Date updatetime, boolean hidden, boolean navbar, ComponentType action) {
        this.id = id;
        this.name = name;
        this.updatetime = updatetime;
        this.hidden = hidden;
        this.navbar = navbar;
        this.action = action;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean getNavbar() {
        return navbar;
    }

    public void setNavbar(boolean navbar) {
        this.navbar = navbar;
    }

    public String getOutputtype() {
        return outputtype;
    }

    public void setOutputtype(String outputtype) {
        this.outputtype = outputtype;
    }

    public ComponentType getAction() {
        return action;
    }

    public void setAction(ComponentType action) {
        this.action = action;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog websiteid) {
        this.weblog = websiteid;
    }

    @XmlTransient
    public List<CustomTemplateRendition> getCustomTemplateRenditionList() {
        return weblogCustomTemplates;
    }

    public void setCustomTemplateRenditionList(List<CustomTemplateRendition> customTemplateRenditionList) {
        this.weblogCustomTemplates = customTemplateRenditionList;
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
        if (!(object instanceof WeblogCustomTemplate)) {
            return false;
        }
        WeblogCustomTemplate other = (WeblogCustomTemplate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogCustomTemplate[ id=" + id + " ]";
    }
    
}
