package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author henry.kastler
 */
public class JsonBuilder {

    private static final Logger log = Logger.getLogger(JsonBuilder.class.getName());

    public JsonBuilder() {
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

        Field[] fields = getAllFieldsForObject(o);
        //get all that don't equal the following filters
        //never want these fields
        fields = Arrays.stream(fields).filter(f -> (!f.getName().equalsIgnoreCase("log")))
                .filter(f -> (!f.getName().equalsIgnoreCase("serialversionuid")))
                .toArray(Field[]::new);
        //the inverses must be filtered out
        //to prevent stackoverflow issues
        //infinite recursion
        fields = filterInverses(fields);
        //filter out any user defined fields
        fields = filterFieldsByName(fields, skipFields);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(o);

                if (fieldValue != null) {
                    log.info("fieldName:" + fieldName);
                    if (fieldValue instanceof String) {
                        builder.add(fieldName, (String) fieldValue);
                    } else if (fieldValue instanceof Integer) {
                        builder.add(fieldName, (Integer) fieldValue);
                    } else if (fieldValue instanceof Boolean) {
                        builder.add(fieldName, (Boolean) fieldValue);
                    } else if (fieldValue instanceof Long) {
                        builder.add(fieldName, (Long) fieldValue);
                    } else if (fieldValue instanceof GregorianCalendar) {
                        Date calDate = new Date(((GregorianCalendar) fieldValue).getTimeInMillis());
                        String formattedCalDate = DateFormatter.jsFormat.format(calDate);
                        builder.add(fieldName, formattedCalDate);
                    } else if (fieldValue instanceof List){
                        log.info("list here");
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for(Object item :(List) fieldValue){
                           
                            arrayBuilder.add(item.toString());
                            //arrayBuilder.add(Json.createObjectBuilder().add(fieldName, item.toString()));
                        }
                        builder.add(fieldName, arrayBuilder);
		    } else {
                        try {
                            String jsonStr = fieldValue.toString();
                            String cleanJson = jsonStr.replace("\\\"", "\"");
                            builder.add(fieldName, cleanJson);
                        } catch (Exception e) {
                            String className = fieldValue.getClass().getName();
                            Logger.getLogger(JsonBuilder.class.getName()).log(Level.SEVERE, null, e);
                            try {
                                //Object fallbackObject = Class.forName(className).newInstance();
                                //fallbackObject.getClass().getTypeName();

                                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                                builder.add(className, arrayBuilder);
                                builder.addNull(fieldName);
                            } catch (Exception ex) {
                                Logger.getLogger(JsonBuilder.class.getName()).log(Level.SEVERE, null, ex);

                            }
                        }
                    }
                } else {
                    builder.addNull(fieldName);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(JsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //builder.add(o.getClass().getName(), builder);
        return builder.build();
    }

    /**
     *
     * @param o
     * @return
     */
    public JsonObject toJsonArray(Object o, String[] skipFields) {

        Field[] classFields = o.getClass().getDeclaredFields();
        Field[] superClassFields = o.getClass().getSuperclass().getDeclaredFields();

        Field[] fields = Stream.concat(Arrays.stream(superClassFields), Arrays.stream(classFields))
                .toArray(Field[]::new);
        //get all that don't equal the following filters
        fields = Arrays.stream(fields).filter(f -> (!f.getName().equalsIgnoreCase("log")))
                .filter(f -> (!f.getName().equalsIgnoreCase("serialversionuid")))
                .toArray(Field[]::new);
        for (String skipField : skipFields) {
            fields = Arrays.stream(fields).filter(f -> (!f.getName().equalsIgnoreCase(skipField)))
                    .toArray(Field[]::new);
        }
        for (Field field : fields) {
            
            Annotation[] annotations = field.getAnnotations();
            for (Annotation ann : annotations) {
                ann.annotationType().getName();

                if (ann.annotationType().getName().equals("javax.persistence.ManyToOne")) {
                    
                    for (Method method : ann.annotationType().getDeclaredMethods()) {
                        
                        if (method.equals("mappedBy")) {
                            fields = Arrays.stream(fields)
                                    .filter(f -> !f.equals(field)).toArray(Field[]::new);
                        }
                    }
                }

            }

        }

        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(o);

                if (fieldValue != null) {
                    if (fieldValue instanceof String) {
                        arrayBuilder.add(Json.createObjectBuilder().add(fieldName, (String) fieldValue));
                    } else if (fieldValue instanceof Integer) {
                        arrayBuilder.add(Json.createObjectBuilder().add(fieldName, (Integer) fieldValue));
                    } else if (fieldValue instanceof Boolean) {
                        arrayBuilder.add(Json.createObjectBuilder().add(fieldName, (Boolean) fieldValue));
                    } else if (fieldValue instanceof Long) {
                        arrayBuilder.add(Json.createObjectBuilder().add(fieldName, (Long) fieldValue));
                    } else {
                        arrayBuilder.add(Json.createObjectBuilder().add(fieldName, fieldValue.toString()));
                    }
                } else {
                    arrayBuilder.add(Json.createObjectBuilder().addNull(fieldName));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(JsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        builder.add(o.getClass().getName(), arrayBuilder);
        return builder.build();
    }

    public Field[] getAllFieldsForObject(Object o) {
        Field[] classFields = o.getClass().getDeclaredFields();
        Field[] superClassFields = o.getClass().getSuperclass().getDeclaredFields();

        Field[] fields = Stream.concat(Arrays.stream(superClassFields), Arrays.stream(classFields))
                .toArray(Field[]::new);
        return fields;
    }

    public Field[] filterFieldsByName(Field[] fieldsToFilter, String[] skipFields) {

        for (String skipField : skipFields) {
            fieldsToFilter = Arrays.stream(fieldsToFilter).filter(f -> (!f.getName().equalsIgnoreCase(skipField)))
                    .toArray(Field[]::new);
        }

        return fieldsToFilter;
    }

    public Field[] filterInverses(Field[] fieldsToFilter) {

        for (Field field : fieldsToFilter) {
            
            Annotation[] annotations = field.getAnnotations();
            for (Annotation ann : annotations) {
                ann.annotationType().getName();
                if (ann.annotationType().getName().equals("javax.persistence.ManyToOne")) {
                    
                    for (Method method : ann.annotationType().getDeclaredMethods()) {
                        
                        if (method.equals("mappedBy")) {
                            fieldsToFilter = Arrays.stream(fieldsToFilter)
                                    .filter(f -> !f.equals(field)).toArray(Field[]::new);
                        }
                    }
                }
            }

        }
        return fieldsToFilter;
    }
}
