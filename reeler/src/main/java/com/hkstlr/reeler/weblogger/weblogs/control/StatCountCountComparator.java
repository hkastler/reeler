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

package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.weblogger.weblogs.control.StatCount;
import java.util.Comparator;
import java.io.Serializable;

/**
 * @author Markus Fuchs
 */
public final class StatCountCountComparator implements Comparator<StatCount>, Serializable {

    private static final long serialVersionUID = 4811314286365625712L;
    
    private static StatCountCountComparator instance = new StatCountCountComparator();

    private StatCountCountComparator() {}

    /** 
     * Compares two <em>StatCount</em> instances according to their count values.
     * 
     * @throws ClassCastException if arguments are not instances of <em>StatCount</em>
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(StatCount sc1, StatCount sc2) {
        int compVal = sc1.getCount() < sc2.getCount() ? -1 :
                (sc1.getCount() == sc2.getCount() ? 0 : 1);
        
        if (compVal == 0) {
            compVal = sc1.getSubjectId().compareTo(sc2.getSubjectId());
            if (compVal == 0) {
                compVal = sc1.getTypeKey().compareTo(sc2.getTypeKey());   
            }
        }
        return compVal;
    }

    public static StatCountCountComparator getInstance() {
        return instance;
    }
}
