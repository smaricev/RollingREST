package me.marichely.Rollin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    Integer id;
    @Column
    String userName;
    @Column
    String name;
    @Column
    String surName;
    @Column
    String email;
    @Column
    String passWord;
    @Column
    String apiKey;
    @Column
    Type type;
}
