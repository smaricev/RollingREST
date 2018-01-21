package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {
    @Column(name = "UserID")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty(value = "userid")
    private Integer id;
    @Column(name = "Username")
    @JsonProperty(value = "username")
    private String userName;
    @Column(name = "Name")
    @JsonProperty(value = "name")
    private String name;
    @Column(name = "Surname")
    @JsonProperty(value = "surname")
    private String surName;
    @Column(name = "Email")
    @JsonProperty(value = "email")
    private String email;
    @Column(name = "Password")
    @JsonProperty(value = "password")
    private String passWord;
    @Column(name = "Apikey")
    @JsonProperty(value = "apikey")
    private String apiKey;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Type")
    @JsonProperty(value = "type")
    private Type type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
