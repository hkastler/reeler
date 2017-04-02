/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.Comparator;
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
@Table(name = "weblogcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogCategory.findAll", query = "SELECT w FROM WeblogCategory w")
    , @NamedQuery(name = "WeblogCategory.findById", query = "SELECT w FROM WeblogCategory w WHERE w.id = :id")
    , @NamedQuery(name = "WeblogCategory.findByName", query = "SELECT w FROM WeblogCategory w WHERE w.name = :name")
    , @NamedQuery(name = "WeblogCategory.findByDescription", query = "SELECT w FROM WeblogCategory w WHERE w.description = :description")
    , @NamedQuery(name = "WeblogCategory.findByImage", query = "SELECT w FROM WeblogCategory w WHERE w.image = :image")
    , @NamedQuery(name = "WeblogCategory.findByPosition", query = "SELECT w FROM WeblogCategory w WHERE w.position = :position")
    , @NamedQuery(name = "WeblogCategory.getByWeblog", query = "SELECT w FROM WeblogCategory w WHERE w.weblog = ?1 order by w.position")
    , @NamedQuery(name = "WeblogCategory.getByWeblog&amp;Name", query = "SELECT w FROM WeblogCategory w WHERE w.weblog = ?1 AND w.name = ?2")
    , @NamedQuery(name = "WeblogCategory.removeByWeblog", query = "DELETE FROM WeblogCategory w WHERE w.weblog = ?1")})
public class WeblogCategory extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "image", length = 255)
    private String image;

    @Basic(optional = false)
    @NotNull
    @Column(name = "position", nullable = false)
    private int position;

    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Weblog weblog;

    public WeblogCategory() {
        super();
    }

    public WeblogCategory(String name, int position) {
        super();
        this.name = name;
        this.position = position;
    }

    public WeblogCategory(Weblog newWeblog, String name, String description, String image) {
        super();
        this.weblog = newWeblog;
        this.name = name;
        this.description = description;
        this.image = image;
        //weblog.getWeblogCategories().add(this);
        calculatePosition();
    }

    public String getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }
    
    public void calculatePosition() {
        int size = weblog.getWeblogCategories().size();
        if(size==0){
            this.position = 0;
        }
       
        if (size == 0) {
            this.position = 0;
        } else {
             Integer maxPosition = weblog.getWeblogCategories().stream()
                .max((wc1, wc2)-> Integer.compare(wc1.getPosition(), wc2.getPosition()))
                .get()
                .getPosition();
            this.position = maxPosition + 1;
        }
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
        if (!(object instanceof WeblogCategory)) {
            return false;
        }
        WeblogCategory other = (WeblogCategory) object;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WeblogCategory[ id=" + id + " ]"
                .concat("[name=" + name + "]")
                .concat("[description=" + description + "]")
                .concat("[image=" + image + "]");
    }

}