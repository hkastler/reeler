/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.users.entities;

import com.hkstlr.reeler.app.control.JsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "jdbcrealm_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JdbcrealmGroup.findAll", query = "SELECT j FROM JdbcrealmGroup j"),
    @NamedQuery(name = "JdbcrealmGroup.findByJdbcrealmGroupId", query = "SELECT j FROM JdbcrealmGroup j WHERE j.jdbcrealmGroupId = :jdbcrealmGroupId"),
    @NamedQuery(name = "JdbcrealmGroup.findByGroupname", query = "SELECT j FROM JdbcrealmGroup j WHERE j.groupname = :groupname")})
public class JdbcrealmGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jdbcrealm_groupid")
    private Integer jdbcrealmGroupId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "groupname")
    private String groupname;
    
    @ManyToMany(mappedBy = "jdbcrealmGroups")
    private List<User> users = new ArrayList<User>();
    
    
    public JdbcrealmGroup() {
    }

    public JdbcrealmGroup(Integer jdbcrealmGroupId) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
    }

    public JdbcrealmGroup(Integer jdbcrealmGroupId, String groupname) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
        this.groupname = groupname;
    }

    public Integer getJdbcrealmGroupId() {
        return jdbcrealmGroupId;
    }

    public void setJdbcrealmGroupId(Integer jdbcrealmGroupId) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

        
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jdbcrealmGroupId != null ? jdbcrealmGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JdbcrealmGroup)) {
            return false;
        }
        JdbcrealmGroup other = (JdbcrealmGroup) object;
        if ((this.jdbcrealmGroupId == null && other.jdbcrealmGroupId != null) || (this.jdbcrealmGroupId != null && !this.jdbcrealmGroupId.equals(other.jdbcrealmGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //skip the manytomany with the owner or jsonbuilder will stackoverflow
        return new JsonBuilder().toJsonString(this,new String[]{"users"});
    }
    
     
    public JsonObject toJsonObject() {
        //skip the manytomany with the owner or jsonbuilder will stackoverflow
        return new JsonBuilder().toJsonObject(this,new String[]{"users"});
    }
    
}
