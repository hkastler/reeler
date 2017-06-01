package com.hkstlr.reeler.weblogger.weblogs.control;

import java.util.Objects;

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
* originally from package org.apache.roller.weblogger.pojos
*/


/**
 * Represents a statistical count.
 */
public class StatCount { 
    
    /** Id of the subject of the statistic */
    private String subjectId;
    
    /** Short name of the subject of the statistic */
    private String subjectNameShort;
    
    /** Long name of the subject of the statistic */
    private String subjectNameLong; 
    
    /** I18N key that describes the type of statistic */
    private String typeKey;
    
    /** The statistical count */    
    private long count;
    
    /** Weblog handle of weblog that stat is associated with, or null if none */
    private String weblogHandle = null;

    public StatCount(String subjectId, String subjectNameShort, String subjectNameLong, String typeKey, long count) {
        this.subjectId = subjectId;
        this.subjectNameShort = subjectNameShort;
        this.subjectNameLong = subjectNameLong;
        this.typeKey = typeKey;
        this.count = count;
    } 
    
    public String getTypeKey() {
        return typeKey;
    }
    
    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }
    
    public long getCount() {
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
    }
    
    public String getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getSubjectNameShort() {
        return subjectNameShort;
    }
    
    public void setSubjectNameShort(String subjectNameShort) {
        this.subjectNameShort = subjectNameShort;
    }
    
    public String getSubjectNameLong() {
        return subjectNameLong;
    }
    
    public void setSubjectNameLong(String subjectNameLong) {
        this.subjectNameLong = subjectNameLong;
    }

    public String getWeblogHandle() {
        return weblogHandle;
    }

    public void setWeblogHandle(String weblogHandle) {
        this.weblogHandle = weblogHandle;
    }
    
    //------------------------------------------------------- Good citizenship

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append(getWeblogHandle());
        buf.append(", ").append(getCount());
        buf.append("}");
        return buf.toString();
    }
    
    @Override
    public int hashCode() {
        return getSubjectId().concat(getTypeKey()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StatCount other = (StatCount) obj;
        if (!Objects.equals(this.subjectId, other.subjectId)) {
            return false;
        }
        return Objects.equals(this.typeKey, other.typeKey);
    }
}