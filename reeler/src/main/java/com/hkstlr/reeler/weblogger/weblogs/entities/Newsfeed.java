/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "newsfeed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Newsfeed.findAll", query = "SELECT n FROM Newsfeed n")
    , @NamedQuery(name = "Newsfeed.findById", query = "SELECT n FROM Newsfeed n WHERE n.id = :id")
    , @NamedQuery(name = "Newsfeed.findByName", query = "SELECT n FROM Newsfeed n WHERE n.name = :name")
    , @NamedQuery(name = "Newsfeed.findByDescription", query = "SELECT n FROM Newsfeed n WHERE n.description = :description")
    , @NamedQuery(name = "Newsfeed.findByLink", query = "SELECT n FROM Newsfeed n WHERE n.link = :link")})
public class Newsfeed extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "link", nullable = false, length = 255)
    private String link;
    
    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Weblog weblog;

    public Newsfeed() {
        //default constructor
    }

    public Newsfeed(String id, String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
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

    public Weblog getWeblog() {
		return weblog;
	}

	public void setWeblog(Weblog weblog) {
		this.weblog = weblog;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Newsfeed)) {
            return false;
        }
        Newsfeed other = (Newsfeed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.Newsfeed[ id=" + id + " ]";
    }
    
}
