package com.gorshkov.orm.entity;

import com.gorshkov.orm.annotation.Column;
import com.gorshkov.orm.annotation.Table;

@Table(name = "Person")
public class Person {
    @Column(name = "person_id")
    private  int id;

    @Column
    private String name;

    @Column
    private int age;
}
