package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "Bicycle")
public class Bicycle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "BicycleID")
    @JsonProperty(value = "bicycleid")
    Integer bicycleID;
    @Column(name = "Name")
    @JsonProperty(value = "name")
    String name;
    @Column(name ="State")
    @JsonProperty(value = "state")
    Integer state;
    @Column(name ="Price_per_hour")
    @JsonProperty(value = "price_per_hour")
    Double pricePerHour;
    @Column(name = "Price_per_day")
    @JsonProperty(value = "price_per_day")
    Double pricePerDay;
    @Column(name = "Currency")
    @JsonProperty(value = "currency")
    String currency;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category")
    @JsonProperty(value = "category")
    Category category;

    public Integer getBicycleID() {
        return bicycleID;
    }

    public void setBicycleID(Integer bicycleID) {
        this.bicycleID = bicycleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
