package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bicycle_pictures")
public class BicyclePicture {
    @Id
    @Column(name = "id")
    private String bicycleID;
    @Column(name = "picture_path")
    private String picture_path;
    @JoinColumn(name ="Bicycle")
    @ManyToOne
    @JsonIgnore
    private Bicycle bicycle;
    @Column(name = "created")
    private Date created;

    public String getBicycleID() {
        return bicycleID;
    }

    public void setBicycleID(String bicycleID) {
        this.bicycleID = bicycleID;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
