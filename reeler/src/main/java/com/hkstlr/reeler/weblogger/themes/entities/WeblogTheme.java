package com.hkstlr.reeler.weblogger.themes.entities;

import java.util.List;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.themes.control.ComponentType;

public abstract class WeblogTheme extends Theme {

	/**
	 * 
	 */
	private static final long serialVersionUID = -750974036911756033L;

	// this is the name that will be used to identify a user customized theme
    public static final String CUSTOM = "custom";

    protected Weblog weblog = null;
    
    public WeblogTheme(){
    	
    }
    
    public WeblogTheme(Weblog weblog) {
        this.weblog = weblog;
    }
        
    public Weblog getWeblog() {
        return this.weblog;
    }	

}
