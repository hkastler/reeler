package com.hkstlr.reeler.weblogger.oauth.boundary.manager;


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 * original in package org.apache.roller.weblogger.business.jpa;
 */



import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.weblogger.oauth.control.Digester;
import com.hkstlr.reeler.weblogger.oauth.entities.OAuthAccessorRecord;
import com.hkstlr.reeler.weblogger.oauth.entities.OAuthConsumerRecord;
import com.hkstlr.reeler.weblogger.weblogs.control.URLStrategy;
import java.io.IOException; 
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthServiceProvider;
import net.oauth.OAuthValidator;
import net.oauth.SimpleOAuthValidator;






/**
 * JPA based OAuth manager implementation.
 */

public class OAuthManager{

    private OAuthValidator validator = new SimpleOAuthValidator();

    /**
     * The logger instance for this class.
     */
    @Inject
    private Logger log;
    
    @PersistenceContext
    private EntityManager em;

   
    protected EntityManager getEntityManager() {
        return em;
    }
             
    public OAuthManager() {
    	
    }
    
    public OAuthServiceProvider getServiceProvider() {
        return new OAuthServiceProvider(
            URLStrategy.getOAuthRequestTokenURL(),
            URLStrategy.getOAuthAuthorizationURL(),
            URLStrategy.getOAuthRequestTokenURL());
    }

    public OAuthValidator getValidator() {
        return validator;
    }

    public OAuthConsumer getConsumer(
            OAuthMessage requestMessage)
            throws IOException, OAuthProblemException {

        OAuthConsumer consumer;
        // try to load from local cache if not throw exception
        String consumer_key = requestMessage.getConsumerKey();

        consumer = getConsumerByKey(consumer_key);

        if(consumer == null) {
            throw new OAuthProblemException("token_rejected");
        }

        return consumer;
    }
    
    /**
     * Get the access token and token secret for the given oauth_token. 
     * @param requestMessage
     * @return 
     * @throws java.io.IOException
     * @throws net.oauth.OAuthProblemException
     */
    public OAuthAccessor getAccessor(OAuthMessage requestMessage)
            throws IOException, OAuthProblemException {

        String consumerToken = requestMessage.getToken();
        OAuthAccessor accessor = null;
        if (!consumerToken.isEmpty()) {
            // caller provided a token, it better be good or else
            accessor = getAccessorByToken(consumerToken);
            if (accessor == null) {
                throw new OAuthProblemException("token_expired");
            }
        }

        String consumerKey = requestMessage.getConsumerKey();
        if (accessor == null && !consumerKey.isEmpty()) {
            // caller provided contumer key, do we have an accessor yet
            accessor = getAccessorByKey(consumerKey);
        }
        return accessor;
    }
    
    public OAuthAccessorRecord findByConsumerKey(String consumerKey){
    	Query query = em.createNamedQuery("OAuthAccessorRecord.findByConsumerkey");
 		query.setParameter("consumerKey", consumerKey);
 		OAuthAccessorRecord record = (OAuthAccessorRecord) query.getSingleResult();
 		return record;
    }
    /**
     * Set the access token 
     */
    public void markAsAuthorized(OAuthAccessor accessor, String userId)
            throws OAuthException {
       
		OAuthAccessorRecord record = findByConsumerKey(accessor.consumer.consumerKey);
         
		record.setUserName(userId);
		record.setAuthorized(Boolean.TRUE);
		em.persist(record);
    }

    /**
     * Generate a fresh request token and secret for a consumer.
     * @param accessor
     * @throws OAuthException
     */
    public void generateRequestToken(OAuthAccessor accessor)
            throws OAuthException {

        // generate oauth_token and oauth_secret
        String consumer_key = accessor.consumer.consumerKey;
        // generate token and secret based on consumer_key

        // for now use md5 of name + current time as token
        String token_data = consumer_key + System.nanoTime();
        
        String token = StringPool.BLANK;
        try {
            token = Digester.MD5(token_data);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OAuthManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        // for now use md5 of name + current time + token as secret
        String secret_data = consumer_key + System.nanoTime() + token;
        String secret = StringPool.BLANK;
        try {
            secret = Digester.MD5(secret_data);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OAuthManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        accessor.requestToken = token;
        accessor.tokenSecret = secret;
        accessor.accessToken = null;

        // add to the local cache
        addAccessor(accessor);
    }
    
    /**
     * Generate a fresh request token and secret for a consumer.
     * @param accessor
     * @throws OAuthException
     */
    public void generateAccessToken(OAuthAccessor accessor)
            throws OAuthException {

        try {
            // generate oauth_token and oauth_secret
            // generate token and secret based on consumer_key
            String consumer_key = accessor.consumer.consumerKey;
            
            OAuthAccessorRecord record = findByConsumerKey(accessor.consumer.consumerKey);
            
            // for now use md5 of name + current time as token
            String token_data = consumer_key + System.nanoTime();
            String token = Digester.MD5(token_data);

            record.setRequestToken(null);
            record.setAccessToken(token);
            em.persist(record);

        } catch (Exception ex) {
            throw new OAuthException("ERROR: generating access token", ex);
        }
    }

    public OAuthConsumer addConsumer(String username, String consumerKey) throws OAuthException {

        OAuthConsumerRecord record = new OAuthConsumerRecord();
        record.setConsumerKey(consumerKey);
        record.setUserName(username);
        record.setConsumerSecret(UUID.randomUUID().toString());

        try {
            em.persist(record);
        } catch (Exception ex) {
            throw new OAuthException("ERROR storing accessor", ex);
        }
        
        return new OAuthConsumer(null,
            record.getConsumerKey(),
            record.getConsumerSecret(),
            getServiceProvider());
    }

    public OAuthConsumer addConsumer(String consumerKey) 
            throws OAuthException {
        if (getConsumer() == null) {
            return addConsumer(null, consumerKey);
        } else {
            throw new OAuthException("ERROR: cannot have more than one site-wide consumer");
        }
    }

    public OAuthConsumer getConsumer(){
        OAuthConsumerRecord record = null;
        try {
            TypedQuery<OAuthConsumerRecord> q = em.createNamedQuery("OAuthConsumerRecord.getSiteWideConsumer",
                    OAuthConsumerRecord.class);
            record = q.getSingleResult();

        } catch (Exception ex) {
            log.log(Level.WARNING,"ERROR fetching site-wide consumer", ex);
        }
        if (record != null) {
            return new OAuthConsumer(
                null,
                record.getConsumerKey(),
                record.getConsumerSecret(),
                getServiceProvider());
        }
        return null;
    }

    public OAuthConsumer getConsumerByUsername(String username) {
        OAuthConsumerRecord record = null;
        try {
            TypedQuery<OAuthConsumerRecord> q = em.createNamedQuery("OAuthConsumerRecord.findByUserName",
                    OAuthConsumerRecord.class);
            q.setParameter(1, username);
            record = q.getSingleResult();

        } catch (Exception ex) {
            log.log(Level.WARNING,"ERROR fetching consumer", ex);
        }
        if (record != null) {
            OAuthConsumer consumer = new OAuthConsumer(
                null,
                record.getConsumerKey(),
                record.getConsumerSecret(),
                getServiceProvider());
            consumer.setProperty("userName", record.getUserName());
            return consumer;
        }
        return null;
    }

    
    //--------------------------------------------- package protected internals

    OAuthConsumer consumerFromRecord(OAuthConsumerRecord record) {
        OAuthConsumer consumer = null;
        if (record != null) {
            consumer = new OAuthConsumer(
                null,
                record.getConsumerKey(),
                record.getConsumerSecret(),
                getServiceProvider());
            if (record.getUserName() != null) {
                consumer.setProperty("userId", record.getUserName());
            }
        }
        return consumer;
    }

    OAuthAccessor accessorFromRecord(OAuthAccessorRecord record) {
        OAuthAccessor accessor = null;
        if (record != null) {
            accessor =
                new OAuthAccessor(getConsumerByKey(record.getConsumerKey()));
            accessor.accessToken = record.getAccessToken();
            accessor.requestToken = record.getRequestToken();
            accessor.tokenSecret = record.getTokenSecret();
            if (record.getAuthorized() != null) {
                accessor.setProperty("authorized", record.getAuthorized());
            }
            if (record.getUserName() != null) {
                accessor.setProperty("userId", record.getUserName());
            }
        }
        return accessor;
    }

    OAuthConsumer getConsumerByKey(String consumerKey) {
        OAuthConsumerRecord record = null;
        try {
            TypedQuery<OAuthConsumerRecord> q = em.createNamedQuery("OAuthConsumerRecord.findByConsumerKey",
                    OAuthConsumerRecord.class);
            q.setParameter(1, consumerKey);
            record = q.getSingleResult();

        } catch (Exception ex) {
        	log.log(Level.WARNING,"ERROR fetching consumer", ex);
        }
        return consumerFromRecord(record);
    }

    void addAccessor(OAuthAccessor accessor) throws OAuthException {

        OAuthAccessorRecord record = new OAuthAccessorRecord();
        record.setConsumerKey(accessor.consumer.consumerKey);
        record.setRequestToken(accessor.requestToken);
        record.setAccessToken(accessor.accessToken);
        record.setTokenSecret(accessor.tokenSecret);
        if (accessor.getProperty("userId") != null) {
            record.setUserName((String)accessor.getProperty("userId"));
        }

        if (record.getCreated() != null) {
            record.setCreated(record.getCreated());
        } else {
            record.setCreated(new Timestamp(new Date().getTime()));
        }
        
        if (record.getUpdated() != null) {
            record.setUpdated(record.getUpdated());
        } else {
            record.setUpdated(record.getCreated());
        }

        if (accessor.getProperty("authorized") != null) {
            record.setAuthorized((Boolean)accessor.getProperty("authorized"));
        }
        try {
            em.persist(record);
        } catch (Exception ex) {
            throw new OAuthException("ERROR storing accessor", ex);
        }
    }

    OAuthAccessor getAccessorByKey(String consumerKey) {
        OAuthAccessorRecord record = null;
        try {
            TypedQuery<OAuthAccessorRecord> q = em.createNamedQuery("OAuthAccessorRecord.findByConsumerKey",
                    OAuthAccessorRecord.class);
            q.setParameter(1, consumerKey);
            record = q.getSingleResult();

        } catch (Exception ex) {
        	log.log(Level.WARNING,"ERROR fetching accessor", ex);
        }
        return accessorFromRecord(record);
    }

    OAuthAccessor getAccessorByToken(String token) {
        OAuthAccessorRecord record = null;
        try {
            TypedQuery<OAuthAccessorRecord> q = em.createNamedQuery("OAuthAccessorRecord.findByToken",
                    OAuthAccessorRecord.class);
            q.setParameter(1, token);
            record = q.getSingleResult();

        } catch (Exception ex) {
        	log.log(Level.WARNING,"ERROR fetching accessor", ex);
        }
        return accessorFromRecord(record);
    }

    void removeConsumer(OAuthConsumer consumer) throws OAuthException {
        try {
        	Query query = em.createQuery("delete from OAuthConsumer oac where oac.consumerKey=:consumerKey" );
        	query.setParameter(1, consumer.consumerKey);
            query.executeUpdate();
        } catch (Exception ex) {
            throw new OAuthException("ERROR removing consumer", ex);
        }
    }

    void removeAccessor(OAuthAccessor accessor) throws OAuthException {
        try {
                      
            Query query = em.createQuery("delete from OAuthAccessor oaa where oaa.consumerKey=:consumerKey" );
        	query.setParameter(1, accessor.consumer.consumerKey);
        	query.executeUpdate();
        } catch (Exception ex) {
            throw new OAuthException("ERROR removing accessor", ex);
        }
    }

	
}

