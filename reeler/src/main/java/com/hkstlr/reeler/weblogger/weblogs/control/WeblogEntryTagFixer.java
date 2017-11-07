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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntryTagFixer {
    
    public WeblogEntryTagFixer() {
        //constructor
    }    
    
    /**
     * @param tag
     * @return
     */
    public static String stripInvalidTagCharacters(String tag) {
        if (tag == null) {
            throw new NullPointerException();
        }

        StringBuilder sb = new StringBuilder();
        char[] charArray = tag.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            // fast-path exclusions quotes and commas are obvious
            // 34 = double-quote, 44 = comma
            if(c == 34 || c == 44){
                continue;
            }
            

            if ((33 <= c && c <= 126) || Character.isUnicodeIdentifierPart(c)
                    || Character.isUnicodeIdentifierStart(c)) {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    public static String normalizeTag(String vtag, Locale locale) {
        String tag = vtag;
        tag = stripInvalidTagCharacters(tag);
        return locale == null ? tag.toLowerCase() : tag.toLowerCase(locale);
    }

    /**
     * @param tags String holding space separated list of tags
     * @return List of strings, one string for each tag
     */
    public static List<String> splitStringAsTags(String tags) {
        String[] tagsarr = tags.split(" *(,|=>| ) *");
        if (tagsarr == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(tagsarr);
    }
}
