package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Column(name = "Name")
    @JsonProperty(value = "name")
    private String name;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="CategoryID")
    @JsonProperty(value = "categoryid")
    private Integer CategoryID;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "category")
    List<Bicycle> bicycles;

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer categoryID) {
        CategoryID = categoryID;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bicycle> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<Bicycle> bicycles) {
        this.bicycles = bicycles;
    }
}
