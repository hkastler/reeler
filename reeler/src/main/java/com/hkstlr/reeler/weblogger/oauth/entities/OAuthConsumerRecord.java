/*
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
 */
package com.hkstlr.reeler.weblogger.oauth.entities;

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
@Table(name = "roller_oauthconsumer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OAuthConsumerRecord.findAll", query = "SELECT r FROM OAuthConsumerRecord r")
    , @NamedQuery(name = "OAuthConsumerRecord.findByConsumerKey", query = "SELECT r FROM OAuthConsumerRecord r WHERE r.consumerKey = :consumerKey")
    , @NamedQuery(name = "OAuthConsumerRecord.findByConsumerSecret", query = "SELECT r FROM OAuthConsumerRecord r WHERE r.consumerSecret = :consumerSecret")
    , @NamedQuery(name = "OAuthConsumerRecord.findByUserName", query = "SELECT r FROM OAuthConsumerRecord r WHERE r.userName = :userName")
    , @NamedQuery(name = "OAuthConsumerRecord.getByConsumerKey", query = "SELECT p FROM OAuthConsumerRecord p WHERE p.consumerKey = ?1")
    , @NamedQuery(name = "OAuthConsumerRecord.getByUsername", query = "SELECT p FROM OAuthConsumerRecord p WHERE p.userName = ?1")
    , @NamedQuery(name = "OAuthConsumerRecord.getSiteWideConsumer", query = "SELECT p FROM OAuthConsumerRecord p WHERE p.userName IS NULL")})
public class OAuthConsumerRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "consumerkey", nullable = false, length = 48)
    private String consumerKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "consumersecret", nullable = false, length = 48)
    private String consumerSecret;
    @Size(max = 48)
    @Column(name = "username", length = 48)
    private String userName;

    public OAuthConsumerRecord() {
        //default constructor
    }

    public OAuthConsumerRecord(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public OAuthConsumerRecord(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
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
        hash += (consumerKey != null ? consumerKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof OAuthConsumerRecord)) {
            return false;
        }
        OAuthConsumerRecord other = (OAuthConsumerRecord) object;
        if ((this.consumerKey == null && other.consumerKey != null) || (this.consumerKey != null && !this.consumerKey.equals(other.consumerKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.OAuthConsumerRecord[ consumerKey=" + consumerKey + " ]";
    }
    
}
