package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class URLStrategy {
    
    @Inject
    WeblogManager wm;

    public URLStrategy() {
        //constructor
    }

    public static String getOAuthRequestTokenURL() {
        // TODO Auto-generated method stub
        return "hey";
    }

    public static String getOAuthAuthorizationURL() {
        // TODO Auto-generated method stub
        return "authorized";
    }
    
    public static String getSetupSuccessOutcome() {
        
        return "/weblogger/login/index";
    }

    public static String getLoginSuccessOutcome() {
        
        return "/weblogger/reeler-ui/index?faces-redirect=true";
    }

    public String getLoginSuccessOutcome(User user) throws WebloggerException {

        List<Weblog> weblogs = wm.getUserWeblogs(user, true);
        if(!weblogs.isEmpty())            
            return "/weblogger/reeler-ui/weblog/entry.xhtml?weblog=".concat(weblogs.get(0).getId()).concat("&faces-redirect=true") ;
        else
           return getLoginSuccessOutcome();
    }

}
