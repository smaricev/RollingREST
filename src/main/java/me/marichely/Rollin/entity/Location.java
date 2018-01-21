package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "Location")
public class Location {

    @Column(name = "Latitude")
    @JsonProperty(value = "latitude")
    private String latitude;

    @Column(name = "Longitude")
    @JsonProperty(value = "longitude")
    private String longitude;

    @Column(name = "Location")
    @JsonProperty(value = "location")
    private String location;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "LocationID")
    @JsonProperty(value = "locationID")
    private Integer locationID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Bicycle")
    @JsonProperty(value = "bicycle")
    private Bicycle bicycle;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }
}
