package com.hkstlr.reeler.weblogger.themes.control;

import com.hkstlr.reeler.app.control.WebloggerException;

/**
 * Thrown when the ThemeManager has a problem finding a named theme.
 */
public class ThemeNotFoundException extends WebloggerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ThemeNotFoundException(String s, Throwable t) {
        super(s, t);
    }

    public ThemeNotFoundException(Throwable t) {
        super(t);
    }

    public ThemeNotFoundException(String s) {
        super(s);
    }

    public ThemeNotFoundException() {
        super();
    }

}
