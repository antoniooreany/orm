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

        String tableName = !tableAnnotation.name().isEmpty() ? tableAnnotation.name() : clazz.getSimpleName();
        //TODO is it correct 'clazz.getSimpleName()'???

        String parameters = getParameters(clazz);
        result.append(parameters)
                .append(" FROM ")
                .append(tableName)
                .append(";");
        return result.toString();
    }

    @Override
    public String findById(Serializable id, Class<?> clazz) {
        Table tableAnnotation = getTableAnnotation(clazz);

        StringBuilder result = new StringBuilder("SELECT ");

        String tableName = !tableAnnotation.name().isEmpty() ? tableAnnotation.name() : clazz.getSimpleName();
        //TODO is it correct 'clazz.getSimpleName()'???

        String parameters = getParameters(clazz);
        result.append(parameters)
                .append(" FROM ")
                .append(tableName)
                .append(" WHERE person_id = ") //TODO make 'person_id' non-hardcoded.
                .append(id)
                .append(";");
        return result.toString();
    }

    // "INSERT INTO Person (person_id, name, age) VALUES ('1', 'John', '33')
    @Override
    public String insert(Object value) throws IllegalAccessException {
        Class<?> clazz = value.getClass();
        Table tableAnnotation = getTableAnnotation(clazz);

        String tableName = !tableAnnotation.name().isEmpty() ? tableAnnotation.name() : clazz.getSimpleName();
        //TODO is it correct 'clazz.getSimpleName()'???

        StringBuilder result = new StringBuilder("INSERT INTO ");

        Field declaredField = clazz.getDeclaredFields()[0];
        Field declaredField1 = clazz.getDeclaredFields()[1];
        Field declaredField2 = clazz.getDeclaredFields()[2];

        declaredField.setAccessible(true);
        declaredField1.setAccessible(true);
        declaredField2.setAccessible(true);
        result
                .append(tableName)
                .append(" (person_id, name, age) VALUES ('")
                .append(declaredField.get(value))
                .append("', '")
                .append(declaredField1.get(value))
                .append("', '")
                .append(declaredField2.get(value))
                .append("');");

        return result.toString();
    }

    //DELETE FROM table_name WHERE id = 0001;
    @Override
    public String delete(Object value) throws IllegalAccessException {
        StringBuilder result = new StringBuilder("DELETE FROM ");

        Class<?> clazz = value.getClass();
        Table tableAnnotation = getTableAnnotation(clazz);

        String tableName = !tableAnnotation.name().isEmpty() ? tableAnnotation.name() : clazz.getSimpleName();

        clazz.getDeclaredFields()[0].setAccessible(true);

        result.append(tableName)
                .append(" WHERE ")
                .append(clazz.getDeclaredFields()[0].getName())
                .append(" = ")
                .append(clazz.getDeclaredFields()[0].get(value))
                .append(";")
        ;

        return result.toString();
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
