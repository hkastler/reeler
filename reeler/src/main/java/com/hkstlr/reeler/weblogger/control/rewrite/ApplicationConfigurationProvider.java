/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.control.rewrite;

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
public class ApplicationConfigurationProvider extends HttpConfigurationProvider
{
   @Override
   public Configuration getConfiguration(ServletContext context){
      return ConfigurationBuilder.begin()
        .addRule().perform(Log.message(Level.DEBUG, "rewrite in the app"))
        
        // Join a URL to an internal resource that accepts a parameter
        .addRule(Join.path("/").to("/index.xhtml"))
        .addRule(Join.path("/{handle}").to("/weblogger/pages/handle.xhtml"))
        .addRule(Join.path("/{handle}/").to("/weblogger/pages/handle.xhtml"))
        .addRule(Join.path("/{handle}/entry/{anchor}").to("/weblogger/pages/entry.xhtml"))
        .addRule(Join.path("/{handle}/category/{categoryName}").to("/weblogger/pages/category.xhtml"))
        .addRule(Join.path("/{handle}/search").to("/weblogger/pages/search.xhtml"))
        .addRule(TrailingSlash.append())
        .when(Path.matches("/{x}"))
        .where("x").matches("^(?!.*\\.xhtml.*).*$");
   }

   @Override
   public int priority(){
      return 0;
   }

   
}
