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
package com.hkstlr.reeler.weblogger.themes.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import org.ocpsoft.logging.Logger;

/**
 *
 * @author henry.kastler
 */
@Startup
@Stateless
public class Test {
    
    private Logger log = Logger.getLogger(Test.class.getName());
    
    public  Test(){
    }
    
    @PostConstruct
    private void init(){
        log.warn("Test Log");
    }
    
}
