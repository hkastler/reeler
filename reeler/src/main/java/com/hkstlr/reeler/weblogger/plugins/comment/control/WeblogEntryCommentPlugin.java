package com.hkstlr.reeler.weblogger.plugins.comment.control;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;

public abstract class WeblogEntryCommentPlugin {
	
	
	 /**
     * Briefly describes the function of the Plugin.  May contain HTML.
     */
    abstract public String getDescription();
    
    
    /**
     * A unique identifier for the plugin.
     */
    abstract public String getId();
    
    
    /**
     * Returns the display name of this Plugin.
     */
    abstract public String getName();
    
    
    /**
     * Apply plugin to the specified text.
     *
     * @param comment     Comment being rendered.
     * @param str         String to which plugin should be applied.
     * @return            Results of applying plugin to string.
     */
    abstract public String render(final WeblogEntryComment comment, String str);
}
