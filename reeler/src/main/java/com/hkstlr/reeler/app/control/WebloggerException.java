package com.hkstlr.reeler.app.control;

public class WebloggerException extends Exception {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public WebloggerException() {
	        super();
	    }
	    
	    
	    /**
	     * Construct WebloggerException with message string.
	     *
	     * @param s Error message string.
	     */
	    public WebloggerException(String s) {
	        super(s);
	    }
	    
	    
	    /**
	     * Construct WebloggerException, wrapping existing throwable.
	     *
	     * @param s Error message
	     * @param t Existing connection to wrap.
	     */
	    public WebloggerException(String s, Throwable t) {
	        super(s, t);
	    }
	    
	    
	    /**
	     * Construct WebloggerException, wrapping existing throwable.
	     *
	     * @param t Existing exception to be wrapped.
	     */
	    public WebloggerException(Throwable t) {
	        super(t);
	    }

}
