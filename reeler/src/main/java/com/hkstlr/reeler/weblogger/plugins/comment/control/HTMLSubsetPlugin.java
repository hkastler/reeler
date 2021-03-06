/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package com.hkstlr.reeler.weblogger.plugins.comment.control;



//import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.plugins.control.StringFixer;


/**
 * Transforms the given String into a subset of HTML.
 */
public class HTMLSubsetPlugin extends WeblogEntryCommentPlugin {
      
    
    public HTMLSubsetPlugin() {
       //default constructor
    }
    
    
    /**
     * Unique identifier.  This should never change. 
     */
    public String getId() {
        return "HTMLSubset";
    }
    
    
    public String getName() {
        return "HTML Subset Restriction";
    }
    
    
    public String getDescription() {
        return "Transforms the given comment body into a subset of HTML";
    }
    
    
    public String render(final WeblogEntryComment comment, String text) {
        String output = text;
        
        // only do this if comment is HTML
        if ("text/html".equals(comment.getContentType())) {
            
            	  
	        // just use old utilities method
	        output = StringFixer.transformToHTMLSubset(output);
	        
        }
                
        return output;
    }
    
}
