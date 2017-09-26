/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.PageBean;
import com.hkstlr.reeler.app.control.Paginator;
import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntrySearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogEntriesBean extends PageBean {

    private static final Logger LOG = Logger.getLogger(WeblogEntriesBean.class.getName());

    @EJB
    private Weblogger weblogger;

    @ManagedProperty(value = "#{reelerUiBean}")
    private ReelerUIBean reelerUiBean;

    private String outcome;

    public WeblogEntriesBean() {
        super();
    }

    @PostConstruct
    public void init() {
        outcome = reelerUiBean.getPath() + StringPool.SLASH + "entries.xhtml";

    }

    public ReelerUIBean getReelerUiBean() {
        return reelerUiBean;
    }

    public void setReelerUiBean(ReelerUIBean reelerUiBean) {
        this.reelerUiBean = reelerUiBean;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void entriesViewAction() {

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        Optional<Boolean> filtered = Optional.ofNullable(
                Boolean.parseBoolean(params.get("filter"))
        );
        

        reelerUiBean.setUserWeblogs();
        Weblog weblog = reelerUiBean.getCurrentWeblog();

        if (filtered.isPresent() && filtered.get()) {
            this.outcome += "?1=1";
            //these will be re-added by the paginator
            params.entrySet()
                    .stream()
                    .filter(p -> !"page".equals(p.getKey()))
                    .filter(p -> !"pageSize".equals(p.getKey()))
                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()))
                    .forEach(
                            (k, v) -> this.outcome += (StringPool.AMPERSAND + k + StringPool.EQUAL + v)
                    );

            WeblogEntrySearchCriteria wesc = new WeblogEntrySearchCriteria();
            wesc.setWeblog(weblog);
            wesc.setText(params.get("searchString"));
            wesc.setCategoryId(params.get("weblogEntryCategory"));
            try {
                Date dt = DateFormatter.datePickerFormat.parse(params.get("startDateString"));
                wesc.setStartDate(dt);
                dt = DateFormatter.datePickerFormat.parse(params.get("endDateString"));
                wesc.setEndDate(dt);
            } catch (ParseException ex) {
                //need a bit better error handling
                LOG.log(Level.FINEST, null, ex);
            }

            String status = params.get("approvedString");
            if (!"ALL".equals(status)) {
                wesc.setStatus(WeblogEntry.PubStatus.valueOf(status));
            }

            List<WeblogEntry> entries = weblogger.getWeblogEntryManager().getBySearchCriteria(wesc);

            int weblogEntryCount = entries.size();
            
            if (pageSize == null) {
                pageSize = weblogEntryCount;
            }
            paginator = new Paginator(pageSize,
                    this.getPageNum(),
                    weblogEntryCount);
            
            weblog.setWeblogEntries(entries);

        } else {
            int weblogEntryCount = weblogger.getWeblogEntryManager().getWeblogEntryCountForWeblog(weblog);
            
            if (pageSize == null) {
                pageSize = weblog.getEntryDisplayCount();
            }
            paginator = new Paginator(weblog.getEntryDisplayCount(),
                    this.getPageNum(),
                    weblogEntryCount);
            int[] range = new int[2];
            range[0] = getPaginator().getPageFirstItem() - 1;
            range[1] = getPaginator().getPageLastItem() - 1;
            List<WeblogEntry> entries = weblogger.getWeblogEntryManager().getAllEntriesPaginated(weblog, range);
            weblog.setWeblogEntries(entries);
        }
        try {
            reelerUiBean.setWeblogPermissions();
        } catch (Exception ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
