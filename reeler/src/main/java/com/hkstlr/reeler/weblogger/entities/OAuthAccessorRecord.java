/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_oauthaccessor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OAuthAccessorRecord.findAll", query = "SELECT r FROM OAuthAccessorRecord r")
    , @NamedQuery(name = "OAuthAccessorRecord.findByConsumerKey", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.consumerKey = :consumerkey")
    , @NamedQuery(name = "OAuthAccessorRecord.findByRequestToken", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.requestToken = :requesttoken")
    , @NamedQuery(name = "OAuthAccessorRecord.findByAccessToken", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.accessToken = :accesstoken")
    , @NamedQuery(name = "OAuthAccessorRecord.findByTokenSecret", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.tokenSecret = :tokensecret")
    , @NamedQuery(name = "OAuthAccessorRecord.findByToken", query = "SELECT p FROM OAuthAccessorRecord p WHERE p.requestToken = :token OR p.accessToken = :token")
    , @NamedQuery(name = "OAuthAccessorRecord.findByCreated", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.created = :created")
    , @NamedQuery(name = "OAuthAccessorRecord.findByUpdated", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.updated = :updated")
    , @NamedQuery(name = "OAuthAccessorRecord.findByUserName", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.userName = :userName")
    , @NamedQuery(name = "OAuthAccessorRecord.findByAuthorized", query = "SELECT r FROM OAuthAccessorRecord r WHERE r.authorized = :authorized")
    , @NamedQuery(name = "OAuthAccessorRecord.getByKey", query = "SELECT p FROM OAuthAccessorRecord p WHERE p.consumerKey = ?1")
    , @NamedQuery(name = "OAuthAccessorRecord.getByToken", query = "SELECT p FROM OAuthAccessorRecord p WHERE p.requestToken = ?1 OR p.accessToken = ?1")
    , @NamedQuery(name = "OAuthAccessorRecord.getByUserName", query = "SELECT p FROM OAuthAccessorRecord p WHERE p.userName = ?1")})
public class OAuthAccessorRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "consumerkey", nullable = false, length = 48)
    private String consumerKey;
    @Size(max = 48)
    @Column(name = "requesttoken", length = 48)
    private String requestToken;
    @Size(max = 48)
    @Column(name = "accesstoken", length = 48)
    private String accessToken;
    @Size(max = 48)
    @Column(name = "tokensecret", length = 48)
    private String tokenSecret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    @Size(max = 48)
    @Column(name = "username", length = 48)
    private String userName;
    @Column(name = "authorized")
    private Boolean authorized;

    public OAuthAccessorRecord() {
    }

    public OAuthAccessorRecord(String consumerkey) {
        this.consumerKey = consumerkey;
    }

    public OAuthAccessorRecord(String consumerkey, Date created, Date updated) {
        this.consumerKey = consumerkey;
        this.created = created;
        this.updated = updated;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerkey) {
        this.consumerKey = consumerkey;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requesttoken) {
        this.requestToken = requesttoken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accesstoken) {
        this.accessToken = accesstoken;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokensecret) {
        this.tokenSecret = tokensecret;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumerKey != null ? consumerKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OAuthAccessorRecord)) {
            return false;
        }
        OAuthAccessorRecord other = (OAuthAccessorRecord) object;
        if ((this.consumerKey == null && other.consumerKey != null) || (this.consumerKey != null && !this.consumerKey.equals(other.consumerKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.OAuthAccessorRecord[ consumerkey=" + consumerKey + " ]";
    }
    
}
