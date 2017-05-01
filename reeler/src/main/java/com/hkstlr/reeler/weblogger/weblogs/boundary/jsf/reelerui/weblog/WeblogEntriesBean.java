/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.PageBean;
import com.hkstlr.reeler.app.control.Paginator;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author henry.kastler
 */

@ManagedBean
@RequestScoped
public class WeblogEntriesBean extends PageBean {

    private static final Logger log = Logger.getLogger(WeblogEntriesBean.class.getName());

    @EJB
    private Weblogger weblogger;
    
    
    @ManagedProperty(value = "#{reelerUiBean}")
    private ReelerUIBean reelerUiBean;
    
    public WeblogEntriesBean() {
        super();
    }
    
    @PostConstruct
    public void init(){
        
    }

    public ReelerUIBean getReelerUiBean() {
        return reelerUiBean;
    }

    public void setReelerUiBean(ReelerUIBean reelerUiBean) {
        this.reelerUiBean = reelerUiBean;
    } 
  
    public void entriesViewAction(){
        
        reelerUiBean.setUserWeblogs();
        Weblog weblog = reelerUiBean.getCurrentWeblog();
        int weblogEntryCount = weblogger.getWeblogEntryManager().getWeblogEntryCountForWeblog(weblog);
        log.info("weblogEntryCount:" + weblogEntryCount);
        if(pageSize == null){
            pageSize = weblog.getEntryDisplayCount();
        }        
        paginator = new Paginator(weblog.getEntryDisplayCount(),
                                  this.getPageNum(),
                                  weblogEntryCount);
        int[] range = new int[2];
        range[0] = getPaginator().getPageFirstItem()-1;
        range[1] = getPaginator().getPageLastItem()-1;
        List<WeblogEntry> entries = weblogger.getWeblogEntryManager().getAllEntriesPaginated(weblog, range);
        weblog.setWeblogEntries(entries);
        
        try {
            reelerUiBean.setWeblogPermissions();
        } catch (Exception ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
}
