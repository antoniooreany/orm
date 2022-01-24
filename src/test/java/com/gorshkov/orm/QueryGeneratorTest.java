package com.gorshkov.orm;

import com.gorshkov.orm.entity.Person;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryGeneratorTest {

    @Test
    public void findAllTest() {
        String expectedQuery = "SELECT person_id, name, age FROM Person;";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void findByIdTest() {
        Serializable id = "0001";
        String expectedQuery = new StringBuilder()
                .append("SELECT person_id, name, age FROM Person WHERE id = ")
                .append(id)
                .append(";").toString();
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findById(id, Person.class);

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void insertTest() throws IllegalAccessException {
        int id = 1;
        String name = "John";
        int age = 33;
        Person person = new Person(id, name, age);
        String expectedQuery = new StringBuilder()
                .append("INSERT INTO Person (person_id, name, age) VALUES ('")
                .append(id)
                .append("', '")
                .append(name)
                .append("', '")
                .append(age)
                .append("');")
                .toString();
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.insert(person);

        assertEquals(expectedQuery, actualQuery);
    }

    //DELETE FROM table_name WHERE id = 1;
    @Test
    public void deleteTest() throws IllegalAccessException {
        int id = 1;
        String name = "John";
        int age = 33;
        Person person = new Person(id, name, age);
        person.getClass().getDeclaredFields()[0].setAccessible(true);
        String expectedQuery = new StringBuilder()
                .append("DELETE FROM ")
                .append(person.getClass().getSimpleName())
                .append(" WHERE ")
                .append(person.getClass().getDeclaredFields()[0].getName())
                .append(" = ")
                .append(id)
                .append(";")
                .toString();
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.delete(person);

        assertEquals(expectedQuery, actualQuery);
    }

}
