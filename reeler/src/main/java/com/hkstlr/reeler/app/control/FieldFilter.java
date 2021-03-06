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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Henry
 */
public class FieldFilter {

    private static final Logger log = Logger.getLogger(FieldFilter.class.getName());

    private Object obj;
    
    private Field[] fields;
    
    public FieldFilter() {
    }
    
    public FieldFilter(Object obj) {
        this.obj = obj;
        this.fields = getAllFieldsForObject();
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }
       
    private Field[] getAllFieldsForObject() {
       
        return Stream.concat(Arrays.stream(this.obj.getClass()
                .getSuperclass()
                .getDeclaredFields()), Arrays.stream(this.obj.getClass()
                .getDeclaredFields()))
                .toArray(Field[]::new);

    }
    
    public void setFilteredFields(String[] skipFields){
        //get all that don't equal the following filters
        //never want these fields
        setFields( filterNonDisplay(this.fields) );
        //prolly don't want the statics neither
        setFields( filterStatics(this.fields) );
        //the inverses must be filtered out
        //to prevent stackoverflow issues
        //infinite recursion
        setFields(filterInverses(this.fields));
        //filter out any user defined fields
        setFields(filterFieldsByName(this.fields, skipFields));
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
                log.finest("annotation type:" + ann.annotationType().getTypeName());
                if ( "javax.persistence.ManyToMany".equals(ann.annotationType().getTypeName())
                        ||
                        "javax.persistence.OneToMany".equals(ann.annotationType().getTypeName())) {
                    log.finer(field.getName() + StringPool.COLON + ann.annotationType().getTypeName());
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
    
    public Field[] filterNonDisplay(Field[] fieldsToFilter){
       return Arrays.stream(fieldsToFilter).filter(f -> !"log".equalsIgnoreCase(f.getName()))
                .filter(f -> !"serialversionuid".equalsIgnoreCase(f.getName()))
                .toArray(Field[]::new);
    }
}
