/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "roller_properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RuntimeConfigProperty.findAll", query = "SELECT r FROM RuntimeConfigProperty r")
    , @NamedQuery(name = "RuntimeConfigProperty.findByName", query = "SELECT r FROM RuntimeConfigProperty r WHERE r.name = :name")
    , @NamedQuery(name = "RuntimeConfigProperty.findByValue", query = "SELECT r FROM RuntimeConfigProperty r WHERE r.value = :value")})
public class RuntimeConfigProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Column(name = "value")
    private String value;

    public RuntimeConfigProperty() {
        //default constructor
    }

    public RuntimeConfigProperty(String name) {
        this.name = name;
    }

    public RuntimeConfigProperty(String name, String defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuntimeConfigProperty)) {
            return false;
        }
        RuntimeConfigProperty other = (RuntimeConfigProperty) object;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.RuntimeConfigProperty[ name=" + name + " ]";
    }

}
