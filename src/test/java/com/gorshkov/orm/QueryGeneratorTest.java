package com.gorshkov.orm;

import com.gorshkov.orm.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryGeneratorTest {

    @Test
    public void findAllTest(){
        String expectedQuery = "SELECT person_id, name, age FROM Person;";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }
}
