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
package com.hkstlr.reeler.weblogger.weblogs.control;


import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;

/**
 *
 * @author henry.kastler
 */
@Dependent
public class WeblogEntryAnchorBuilder {
    
    public static final String TITLE_SEPARATOR
            = WebloggerConfig.getBooleanProperty("weblogentry.title.useUnderscoreSeparator") ? "_" : "-";
        
    
    public WeblogEntryAnchorBuilder() {
        //constructor
    }
    
    public String createAnchorBase(@NotNull WeblogEntry weblogEntry){
        return createAnchorBase(weblogEntry, 7);
    }
    
    /** Create anchor for weblog entry, based on title or text */
    public String createAnchorBase(@NotNull WeblogEntry weblogEntry, @NotNull Integer numberOfWordsInUrl) {
        
        String title = weblogEntry.getTitle();
        String text = weblogEntry.getText();
        Calendar pubTime = weblogEntry.getPubTime();
        // Use title (minus non-alphanumeric characters)
        StringBuilder base = new StringBuilder();
        if (!title.isEmpty()) {
            base.append(StringChanger.replaceNonAlphanumeric(title, ' ').trim());    
        }
        // If we still have no base, then try text (minus non-alphanumerics)
        if (base.length() == 0 && !text.isEmpty()) {
            base.append(StringChanger.replaceNonAlphanumeric(text, ' ').trim());  
        }
        
        if (base.length() > 0) {            
            // Use only the first 7 words
            StringTokenizer toker = new StringTokenizer(base.toString());
            StringBuilder tmp = new StringBuilder();
            int count = 0;
            while (toker.hasMoreTokens() && count < numberOfWordsInUrl) {
                String s = toker.nextToken();
                s = s.toLowerCase();
                if(tmp.length() == 0){
                    tmp.append(s);                            
                }else{
                    tmp.append(TITLE_SEPARATOR + s);
                }
                count++;
            }
            base = tmp;
        }
        // No title or text, so instead we will use the items date
        // in YYYYMMDD format as the base anchor
        else {
            Date baseDate = new Date(pubTime.getTimeInMillis());
            base.append(DateFormatter.format8chars.format(baseDate));
        }
        
        return base.toString();
    }
}
