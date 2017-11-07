/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * derived from roller db table, by netbeans, sometime 2017
 */
package com.hkstlr.reeler.weblogger.users.entities;

import com.hkstlr.reeler.app.control.JsonBuilder;
import com.hkstlr.reeler.app.entities.AbstractEntity;
import com.hkstlr.reeler.app.entities.PermissionEntity;
import com.hkstlr.reeler.weblogger.users.control.UserEntityListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@EntityListeners(UserEntityListener.class)
@Table(name = "roller_user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findById", query = "SELECT r FROM User r WHERE r.id = :id")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT r FROM User r WHERE r.userName = :userName")
    , @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.getByEnabled", query = "SELECT u FROM User u WHERE u.isEnabled = ?1")
    , @NamedQuery(name = "User.getUserByActivationCode", query = "SELECT u FROM User u WHERE u.activationCode = ?1")
    , @NamedQuery(name = "User.getByEnabled&EndDateOrderByStartDateDesc", query = "SELECT u FROM User u WHERE u.isEnabled = ?1 AND u.dateCreated < ?2 ORDER BY u.dateCreated DESC")
    , @NamedQuery(name = "User.getByEnabled&EndDate&StartDateOrderByStartDateDesc", query = "SELECT u FROM User u WHERE u.isEnabled = ?1 AND u.dateCreated < ?2 AND u.dateCreated > ?3 ORDER BY u.dateCreated DESC")
    , @NamedQuery(name = "User.getByEnabled&UserNameOrEmailAddressStartsWith", query = "SELECT u FROM User u WHERE u.isEnabled = ?1 AND (u.userName LIKE ?2 OR u.emailAddress LIKE ?3)")
    , @NamedQuery(name = "User.getByEndDateOrderByStartDateDesc", query = "SELECT u FROM User u WHERE u.dateCreated < ?1 ORDER BY u.dateCreated DESC")
    , @NamedQuery(name = "User.getByUserName", query = "SELECT u FROM User u WHERE u.userName= ?1")
    , @NamedQuery(name = "User.getByUserName&Enabled", query = "SELECT u FROM User u WHERE u.userName= ?1 AND u.isEnabled = ?2")
    , @NamedQuery(name = "User.getByOpenIdUrl", query = "SELECT u FROM User u WHERE u.openIdUrl = ?1")
    , @NamedQuery(name = "User.getByUserNameOrEmailAddressStartsWith", query = "SELECT u FROM User u WHERE u.userName LIKE ?1 OR u.emailAddress LIKE ?1")
    , @NamedQuery(name = "User.getByUserNameOrderByUserName", query = "SELECT u FROM User u WHERE u.userName= ?1 ORDER BY u.userName")
    , @NamedQuery(name = "User.getByEndDate&StartDateOrderByStartDateDesc", query = "SELECT u FROM User u WHERE u.dateCreated < ?1 AND u.dateCreated > ?2 ORDER BY u.dateCreated DESC")
    , @NamedQuery(name = "User.getCountByUserNameLike", query = "SELECT COUNT(u) FROM User u WHERE UPPER(u.userName) LIKE ?1")
    , @NamedQuery(name = "User.getCountEnabledDistinct", query = "SELECT COUNT(u) FROM User u WHERE u.isEnabled = ?1")})
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message="{User.userName.NotNull}")
    @Column(name = "username", nullable = false, length = 255, unique = true)
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message="{User.password.NotNull}")
    @Column(name = "passphrase", nullable = false, length = 255)
    private String password;

    @Size(max = 255)
    @Column(name = "openid_url", length = 255)
    private String openIdUrl;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message="{User.screenName.NotNull}")
    @Column(name = "screenname", nullable = false, length = 255)
    private String screenName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message="{User.fullName.NotNull}")
    @Column(name = "fullname", nullable = false, length = 255)
    private String fullName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message="{User.emailAddress.NotNull}")
    @Column(name = "emailaddress", nullable = false, length = 255)
    private String emailAddress;

    @Size(max = 48)
    @Column(name = "activationcode", length = 48)
    private String activationCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Size(max = 20)
    @Column(name = "locale", length = 20)
    private String locale;

    @Size(max = 50)
    @Column(name = "timezone", length = 50)
    private String timeZone;

    @Basic(optional = false)
    @Column(name = "isenabled", nullable = false)
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_jdbcrealm_groups",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "groupname", referencedColumnName = "groupname"))
    private List<JdbcrealmGroup> jdbcrealmGroups = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "useruserrole",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "rolename", referencedColumnName = "rolename")
    )
    private Set<UserRole> roles;

    @Transient
    private transient List<PermissionEntity> permissions = new ArrayList<>();

    public User() {
        //default constructor
    }

    public User(String userName, String password, String screenname, String fullname, String emailaddress, Date datecreated, boolean isenabled) {
        this.roles = new HashSet<>();

        this.userName = userName;
        this.password = password;
        this.screenName = screenname;
        this.fullName = fullname;
        this.emailAddress = emailaddress;
        this.dateCreated = datecreated;
        this.isEnabled = isenabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationcode) {
        this.activationCode = activationcode;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isenabled) {
        this.isEnabled = isenabled;
    }

    public List<JdbcrealmGroup> getJdbcrealmGroups() {
        return jdbcrealmGroups;
    }

    public void setJdbcrealmGroups(List<JdbcrealmGroup> jdbcrealmGroups) {
        this.jdbcrealmGroups = jdbcrealmGroups;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
    
    @Override
    public String toString() {
        //skip the manytomany with the owner or jsonbuilder will stackoverflow
        return new JsonBuilder().toJsonString(this,new String[]{"users"});
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return this.id.equals(other.id);
    }
        
    

}
