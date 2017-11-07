/*
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
package com.hkstlr.reeler.weblogger.weblogs.control.entitylisteners;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntityListener {
//      
    
    Logger log = Logger.getLogger(WeblogEntityListener.class.getName());
    
    @PrePersist
    @PreUpdate
    public void weblogSetDates(Weblog weblog) {
        log.info("Listening Weblog Pre Update : " + weblog.getName());
        if(weblog.getDateCreated()==null){
            log.info("Listening Weblog Pre Update : getDateCreated()==null" );
            weblog.setDateCreated(new Date());
        }
        weblog.setLastModified(new java.util.Date());
    }

    

    @PostPersist
    public void weblogPostPersist(Weblog ob) {
        //System.out.println("Listening Weblog Post Persist : " + ob.getName());
    }

    @PostLoad
    public void weblogPostLoad(Weblog ob) {
        //System.out.println("Listening Weblog Post Load : " + ob.getName());
        //List<WeblogCategory> weblogCategories = wcm.getWeblogCategoriesForWeblog(ob);
        //System.out.println("weblogCategories:" + ob.getWeblogCategories().size());
        //ob.setWeblogCategories(weblogCategories);
    }


    @PostUpdate
    public void weblogPostUpdate(Weblog ob) {
        //System.out.println("Listening Weblog Post Update : " + ob.getName());
    }

    @PreRemove
    public void weblogPreRemove(Weblog ob) {
        //System.out.println("Listening Weblog Pre Remove : " + ob.getName());
    }

    @PostRemove
    public void weblogPostRemove(Weblog ob) {
        //System.out.println("Listening Weblog Post Remove : " + ob.getName());
    }
}
