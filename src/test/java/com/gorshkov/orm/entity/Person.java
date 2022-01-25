package com.gorshkov.orm.entity;

import com.gorshkov.orm.annotation.Column;
import com.gorshkov.orm.annotation.Id;
import com.gorshkov.orm.annotation.Table;

@Table(name = "")
public class Person {
    @Id
    @Column(name = "person_id")
    private  int id;

    @Column
    private String name;

    @Column
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
