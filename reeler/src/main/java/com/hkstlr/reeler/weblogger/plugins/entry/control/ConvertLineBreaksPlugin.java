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
package com.hkstlr.reeler.weblogger.plugins.entry.control;

import com.hkstlr.reeler.app.control.StringPool;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.plugins.control.StringFixer;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.logging.Level;

/**
 * Simple page plugin that converts paragraphs of plain text into html
 * paragraphs. We wrap each full paragraph in html &lt;p&gt; opening and closing
 * tags, and also add &lt;br&gt; tags to the end of lines with breaks inside a
 * paragraph.
 *
 * Example: This is one paragraph
 *
 * Becomes: &lt;p&gt;This is one&lt;br/&gt; paragraph&lt;/p&gt;
 *
 */
public class ConvertLineBreaksPlugin extends WeblogEntryPlugin {

    @Inject
    private Logger mLogger;

    private static final String NAME = "Convert Line Breaks";
    private static final String DESCRIPTION = "Convert plain text paragraphs to html by adding p and br tags";
    private static final String VERSION = "0.1";

    public ConvertLineBreaksPlugin(Class pluginClass) {
        super(pluginClass);

    }

    public ConvertLineBreaksPlugin() {
        super();

    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public void init(Weblog website) throws WebloggerException {
        // we don't need to do any init.

    }

    /**
     * Transform the given plain text into html text by inserting p and br tags
     * around paragraphs and after line breaks.
     */
    public String render(WeblogEntry entry, String str) {

        if (str == null || StringPool.BLANK.equals(str.trim())) {
            return StringPool.BLANK;
        }

        mLogger.fine("Rendering string of length " + str.length());

        return StringFixer.newLineToHtml(str);
    }

}
