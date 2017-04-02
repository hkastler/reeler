/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.ozark.pages;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


/**
 *
 * @author henry.kastler
 */
@Controller
@Path("hello")
public class HelloController {
    
    
    @Inject
    Models models;

    @GET
    public String sayHello(@QueryParam("name") String name) {
        
        this.models.put("text", "Hello " + name);
        return "/WEB-INF/weblogger/hello.xhtml";
        
    }
}
