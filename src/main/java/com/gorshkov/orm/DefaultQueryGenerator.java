package com.gorshkov.orm;

import com.gorshkov.orm.annotation.Column;
import com.gorshkov.orm.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class DefaultQueryGenerator implements QueryGenerator {
    @Override
    public String findAll(Class<?> clazz) {
        Table tableAnnotation = getTableAnnotation(clazz);

        StringBuilder result = new StringBuilder("SELECT ");
        String tableName = !tableAnnotation.name().isEmpty() ? tableAnnotation.name() : clazz.getName();

        String parameters = getParameters(clazz);
        result.append(parameters)
                .append(" FROM ")
                .append(tableName)
                .append(";");
        return result.toString();
    }

    @Override
    public String findById(Serializable id, Class<?> clazz) {
        return null;
    }

    @Override
    public String insert(Object value) {
        return null;
    }

    @Override
    public String delete(Object value) {
        return null;
    }

    private String getParameters(Class<?> clazz) {
        StringJoiner parameters = new StringJoiner(", ");
        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String fieldName = !columnAnnotation.name().isEmpty() ? columnAnnotation.name() : declaredField.getName();
                parameters.add(fieldName);
            }
        }
        return parameters.toString();
    }

    private Table getTableAnnotation(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("");
        }
        return tableAnnotation;
    }
}
