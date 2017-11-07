/*
 *
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
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author henry.kastler
 */
public abstract class AuthorBean<T> {
    
    private Class<T> entityClass;
    
    @EJB
    Weblogger weblogger;
    
    @ManagedProperty(value = "#{reelerUiBean}")
    ReelerUIBean reelerUiBean;
    
    @ManagedProperty(value = "#{param.id}")
    protected String id;
    
    @ManagedProperty(value = "#{param.action}")
    protected String action;
    
    protected String actionLabel;

    public AuthorBean() {
        //default constructor
    }
    
    public AuthorBean(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(String actionLabel) {
        this.actionLabel = actionLabel;
    }

    public Weblogger getWeblogger() {
        return weblogger;
    }

    public void setWeblogger(Weblogger weblogger) {
        this.weblogger = weblogger;
    }

    public ReelerUIBean getReelerUiBean() {
        return reelerUiBean;
    }

    public void setReelerUiBean(ReelerUIBean reelerUiBean) {
        this.reelerUiBean = reelerUiBean;
    }
    
    public void setActionLabel(){
        if("create".equals(action)){
            actionLabel = "Create";
        }else if("edit".equals(action)){
            actionLabel = "Edit";
        }
    }
       
    
    
}
