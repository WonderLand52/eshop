package com.rudenkoInc.eshop.inject;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class FieldReflector {

    public static List<Field> collectAll(Class<?> clazz0, Class<?> clazz1){
        ArrayList<Field> fields = new ArrayList<>();
        Class<?> current = clazz0;
        while (current != clazz1){
            fields.addAll(asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    public static List<Field> filterInject(List<Field> allFields){
        ArrayList<Field> filteredFields = new ArrayList<>();
        for(Field field: allFields){
            Inject annotation = field.getAnnotation(Inject.class);
            if(annotation != null){
                filteredFields.add(field);
            }
        }
        return filteredFields;
    }
}
