/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
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
@Table(name = "userrole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
    , @NamedQuery(name = "UserRole.findById", query = "SELECT u FROM UserRole u WHERE u.id = :id")
    , @NamedQuery(name = "UserRole.findByRolename", query = "SELECT u FROM UserRole u WHERE u.roleName = :roleName")
    , @NamedQuery(name = "UserRole.findByUsername", query = "SELECT u FROM UserRole u WHERE u.userName = :userName")
    , @NamedQuery(name = "UserRole.getByUserName", query = "SELECT r FROM UserRole r WHERE r.userName = ?1")
    , @NamedQuery(name = "UserRole.getByUserNameAndRole", query = "SELECT r FROM UserRole r WHERE r.userName = ?1 AND r.roleName = ?2")})
public class UserRole extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rolename", nullable = false, length = 255)
    private String roleName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username", nullable = false, length = 255)
    private String userName;

    public UserRole() {
    }

    public UserRole(String id, String rolename, String username) {
        this.roleName = rolename;
        this.userName = username;
    }

    public String getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.UserRole[ id=" + id + " ]";
    }

}
