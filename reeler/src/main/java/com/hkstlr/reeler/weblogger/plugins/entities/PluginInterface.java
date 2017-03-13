/*
 * Author henry.kastler hkstlr.com 2017 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hkstlr.reeler.weblogger.plugins.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.entities.Weblog;

/**
 *
 * @author henry.kastler
 */
public interface PluginInterface<T> {

    /**
     * Briefly describes the function of the Plugin.  May contain HTML.
     */
    String getDescription();

    /**
     * Returns the display name of this Plugin.
     */
    String getName();

    /**
     * Give plugin a chance to initialize and add objects the rendering model.
     *
     * @param weblog     Weblog being processed
     */
    void init(Weblog weblog) throws WebloggerException;

    /**
     * Apply plugin to the specified text.
     *
     * @param entry       Entry being rendered.
     * @param str         String to which plugin should be applied.
     * @return            Results of applying plugin to entry.
     */
    String render(Class<T> pluginClass, String str);
    
}
