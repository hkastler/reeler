package com.hkstlr.reeler.app.control;

import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
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

        Field[] fields = getAllFieldsForObject(o);
        //get all that don't equal the following filters
        //never want these fields
        fields = Arrays.stream(fields).filter(f -> !"log".equalsIgnoreCase(f.getName()))
                .filter(f -> !"serialversionuid".equalsIgnoreCase(f.getName()))
                .toArray(Field[]::new);
        //prolly don't want the statics neither
        fields = filterStatics(fields);
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
                        for (Object item : (List) fieldValue) {
                            arrayBuilder.add(this.toJsonObject(item, new String[]{}));
                        }
                        builder.add(fieldName, arrayBuilder);
                    } else if (fieldValue instanceof Set) {
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for (Object item : (Set) fieldValue) {
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

    /**
     *
     * @param o
     * @param skipFields
     * @return
     */
    public JsonObject toJsonArray(Object o, String[] skipFields) {

        Field[] classFields = o.getClass().getDeclaredFields();
        Field[] superClassFields = o.getClass().getSuperclass().getDeclaredFields();

        Field[] fields = Stream.concat(Arrays.stream(superClassFields), Arrays.stream(classFields))
                .toArray(Field[]::new);
        //get all that don't equal the following filters
        fields = Arrays.stream(fields).filter(f -> !"log".equalsIgnoreCase(f.getName()))
                .filter(f -> !"serialversionuid".equalsIgnoreCase(f.getName()))
                .toArray(Field[]::new);
        fields = filterInverses(fields);
        //filter out any user defined fields
        fields = filterFieldsByName(fields, skipFields);

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

        return Stream.concat(Arrays.stream(superClassFields), Arrays.stream(classFields))
                .toArray(Field[]::new);

    }

    public Field[] filterFieldsByName(Field[] fieldsToFilter, String[] skipFields) {
        Field[] lFields = fieldsToFilter;
        for (String skipField : skipFields) {
            lFields = Arrays.stream(lFields).filter(f -> !skipField.equalsIgnoreCase(f.getName()))
                    .toArray(Field[]::new);
        }

        return lFields;
    }

    public Field[] filterInverses(Field[] fieldsToFilter) {

        Field[] lFields = fieldsToFilter;
        for (Field field : fieldsToFilter) {

            Annotation[] annotations = field.getAnnotations();
            for (Annotation ann : annotations) {
                if ( "javax.persistence.ManyToMany".equals(ann.annotationType().getName())
                        ||
                        "javax.persistence.OneToMany".equals(ann.annotationType().getName())) {
                    log.finer(field.getName() + StringPool.COLON + ann.annotationType().getName());
                    for (Method method : ann.annotationType().getDeclaredMethods()) {
                        if ("mappedBy".equals(method.getName())) {
                            try {
                                Object value = method.invoke(ann, (Object[]) null);

                                if (value != null && value.toString().length() > 0) {
                                    lFields = Arrays.stream(lFields)
                                            .filter(f -> !f.equals(field)).toArray(Field[]::new);
                                }
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                log.log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }

        }
        return lFields;
    }

    public Field[] filterStatics(Field[] fieldsToFilter) {

        Field[] lFields = fieldsToFilter;
        for (Field field : fieldsToFilter) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                lFields = Arrays.stream(lFields)
                        .filter(f -> !f.equals(field)).toArray(Field[]::new);
            }

        }
        return lFields;
    }

}
