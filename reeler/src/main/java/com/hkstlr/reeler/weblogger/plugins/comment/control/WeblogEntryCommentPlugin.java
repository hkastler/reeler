package com.hkstlr.reeler.weblogger.plugins.comment.control;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;

public abstract class WeblogEntryCommentPlugin {
	
	
	 /**
     * Briefly describes the function of the Plugin.  May contain HTML.
     */
    public abstract String getDescription();
    
    
    /**
     * A unique identifier for the plugin.
     */
    public abstract String getId();
    
    
    /**
     * Returns the display name of this Plugin.
     */
    public abstract String getName();
    
    
    /**
     * Apply plugin to the specified text.
     *
     * @param comment     Comment being rendered.
     * @param str         String to which plugin should be applied.
     * @return            Results of applying plugin to string.
     */
    public abstract String render(final WeblogEntryComment comment, String str);
}
