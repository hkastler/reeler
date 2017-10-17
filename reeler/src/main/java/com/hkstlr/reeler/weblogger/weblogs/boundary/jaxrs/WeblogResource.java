/*
 * Copyright 2017 Henry.
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
package com.hkstlr.reeler.weblogger.weblogs.boundary.jaxrs;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryCommentManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Henry
 */
@Path("weblog")
public class WeblogResource {
        
    @EJB
    private WeblogEntryManager wem;
    
    @EJB
    private WeblogEntryCommentManager wecm;

    @GET
    @Path("entry-count/{handle}")
    public String getEntryCount(@PathParam("handle") String handle) {
        
        return Integer.toString(wem.getWeblogEntryCountForWeblog(handle));
    }
    
    @GET
    @Path("comments-count/{handle}")
    public String getCommentCount(@PathParam("handle") String handle) throws WebloggerException {
        //System.out.println("GET");
        return Integer.toString(wecm.getCommentCountForWeblog(handle));
    }

    
}
