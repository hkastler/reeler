package com.hkstlr.reeler.weblogger.themes.entities;


import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.Objects;

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
    
    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
