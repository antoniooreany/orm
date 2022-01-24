package com.gorshkov.orm;

import java.io.Serializable;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Serializable id, Class<?> clazz);

    String insert(Object value) throws IllegalAccessException;

    String delete(Object value) throws IllegalAccessException;
}
