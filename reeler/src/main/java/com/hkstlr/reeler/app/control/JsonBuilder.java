/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
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
package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author henry.kastler
 */
public class JsonBuilder {

    private static final Logger log = Logger.getLogger(JsonBuilder.class.getName());

    public JsonBuilder() {
        //default constructor
    }

    public String toJsonString(Object o) {
        return toJsonObject(o, new String[]{}).toString();
    }

    public String toJsonString(Object o, String[] skipFields) {
        return toJsonObject(o, skipFields).toString();
    }

    /**
     *
     * @param o
     * @param skipFields
     * @return
     */
    public JsonObject toJsonObject(Object o, String[] skipFields) {
        FieldFilter fieldFilter = new FieldFilter(o);
        fieldFilter.setFilteredFields(skipFields);    
        
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Field field : fieldFilter.getFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(o);

                if (fieldValue != null) {
                    
                    if (fieldValue instanceof String) {
                        builder.add(fieldName, (String) fieldValue);
                    } else if (fieldValue instanceof Integer) {
                        builder.add(fieldName, (Integer) fieldValue);
                    } else if (fieldValue instanceof Boolean) {
                        builder.add(fieldName, (Boolean) fieldValue);
                    } else if (fieldValue instanceof Long) {
                        builder.add(fieldName, (Long) fieldValue);
                    } else if (fieldValue instanceof Date) {
                        String formattedCalDate = DateFormatter.jsFormat.format((Date) fieldValue);
                        builder.add(fieldName, formattedCalDate);
                    } else if (fieldValue instanceof GregorianCalendar) {
                        Date calDate = new Date(((GregorianCalendar) fieldValue).getTimeInMillis());
                        String formattedCalDate = DateFormatter.jsFormat.format(calDate);
                        builder.add(fieldName, formattedCalDate);
                    } else if (fieldValue instanceof List) {
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for (Object item : (List<?>) fieldValue) {
                            arrayBuilder.add(this.toJsonObject(item, new String[]{}));
                        }
                        builder.add(fieldName, arrayBuilder);
                    } else if (fieldValue instanceof Set) {
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for (Object item : (Set<?>) fieldValue) {
                            arrayBuilder.add(this.toJsonObject(item, new String[]{}));
                        }
                        builder.add(fieldName, arrayBuilder);
                    }else  {
                       
                        builder.add(fieldName,fieldValue.toString());
                    }
                    
                }else {
                    builder.addNull(fieldName);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }

        return builder.build();
    }   

   

}
