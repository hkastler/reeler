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

import java.util.logging.Logger;

import javax.inject.Inject;

//import org.apache.commons.lang3.StringEscapeUtils;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.plugins.control.StringFixer;

/**
 * Obfuscate email addresses in entry text.
 */
public class ObfuscateEmailPlugin extends WeblogEntryPlugin {

    @Inject
    private Logger mLogger;

    protected String name = "Email Scrambler";

    protected String description = "Automatically converts email addresses "
            + "to me-AT-mail-DOT-com format.  Also &quot;scrambles&quot; mailto: links.";

    public ObfuscateEmailPlugin() {
        super();

    }

    public ObfuscateEmailPlugin(Class pluginClass) {
        super(pluginClass);
        mLogger.fine("ObfuscateEmailPlugin instantiated.");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return null;//StringEscapeUtils.escapeEcmaScript(description);
    }

    public String render(WeblogEntry entry, String str) {
        return StringFixer.encodeEmail(str);
    }

}
