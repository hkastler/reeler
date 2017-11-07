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
package com.hkstlr.reeler.weblogger.weblogs.control.jsf.converters;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogCategoryManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogCategoryConverter implements Converter {

    @EJB
    private WeblogCategoryManager wcm;

    
    private Logger log = Logger.getLogger(WeblogCategoryConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        try {
            String id = value;
            //.find apparently only works with numeric fields
            log.info("id:" + id);
            WeblogCategory wc = wcm.findById(id);//session.load(CatalogValue .class, id);
            log.info("returned WeblogCategory:" + wc.toString());
            return wc;
        } catch (Exception ex) {
            log.info("yes it's a fail");
            String message = ex.getMessage();
            log.log(Level.INFO,"reason:",ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((WeblogCategory) value).getId();
    }

}
