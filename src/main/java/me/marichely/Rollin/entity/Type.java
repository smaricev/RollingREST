package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "User_type")
public class Type {
    @Column(name = "UserTypeID")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty(value = "usertypeid")
    private Integer id;
    @Column(name = "Name")
    @JsonProperty(value = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
