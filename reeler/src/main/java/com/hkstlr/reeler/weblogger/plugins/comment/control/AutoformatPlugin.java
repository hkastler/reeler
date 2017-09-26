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

import com.hkstlr.reeler.weblogger.plugins.control.StringFixer;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.util.logging.Logger;


/**
 * Comment plugin which turns plain text paragraph formatting into html
 * paragraph formatting using <p> and <br/> tags.
 */
public class AutoformatPlugin extends WeblogEntryCommentPlugin {

    private static final Logger LOG = Logger.getLogger(AutoformatPlugin.class.getName());
    
    
    
    public AutoformatPlugin() {
        // no-op
    }
    
    
    /**
     * Unique identifier.  This should never change. 
     */
    public String getId() {
        return "AutoFormat";
    }
    
    
    public String getName() {
        return "Auto Format";
    }
    
    
    public String getDescription() {
        return "Converts plain text style paragraphs into html paragraphs.";
    }
    
    
    public String render(final WeblogEntryComment comment, String text) {
        
        LOG.fine("starting value:\n" + text);
        
        return StringFixer.newLineToHtml(text);
    }
    
}
