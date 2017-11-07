/*
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
 * 
 */
package com.hkstlr.reeler.weblogger.weblogs.control.rewrite;

import javax.servlet.ServletContext;

import org.ocpsoft.logging.Logger.Level;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.config.rule.TrailingSlash;

@RewriteConfiguration
public class ApplicationConfigurationProvider extends HttpConfigurationProvider {

    private static final String WEBLOG_PATH = "/weblogger/pages/weblog.xhtml";
    private static final String CATEGORY_PATH = "/weblogger/pages/category.xhtml";
    private static final String DATE_PATH = "/weblogger/pages/date.xhtml";
    private static final String PARAMS_PATH = "/page/{page}/pageSize/{pageSize}";
    
    @Override
    public Configuration getConfiguration(ServletContext context) {
        return ConfigurationBuilder.begin()
                .addRule().perform(Log.message(Level.DEBUG, "rewrite in the app"))
                // Join a URL to an internal resource that accepts a parameter
                .addRule(Join.path("/").to("/index.xhtml"))
                .addRule(Join.path("/{handle}").to(WEBLOG_PATH))
                .addRule(Join.path("/{handle}/").to(WEBLOG_PATH))
                .addRule(Join.path("/{handle}".concat(PARAMS_PATH)).to(WEBLOG_PATH))
                .addRule(Join.path("/{handle}/entry/{anchor}").to("/weblogger/pages/entry.xhtml"))
                .addRule(Join.path("/{handle}/entry-m/{anchor}").to("/weblogger/pages/entryMustache.xhtml"))
                .addRule(Join.path("/{handle}/category/{categoryName}").to(CATEGORY_PATH))
                .addRule(Join.path("/{handle}/category/{categoryName}".concat(PARAMS_PATH))
                        .to(CATEGORY_PATH))
                .addRule(Join.path("/{handle}/search").to("/weblogger/pages/search.xhtml"))
                .addRule(Join.path("/{handle}/date/{dateString}").to(DATE_PATH))
                .addRule(Join.path("/{handle}/date/{dateString}".concat(PARAMS_PATH)).to(DATE_PATH))
                .addRule(TrailingSlash.append())
                .when(Path.matches("/{x}"))
                .where("x").matches("^(?!.*\\.xhtml.*).*$");
    }

    @Override
    public int priority() {
        return 0;
    }

}
