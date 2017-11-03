package me.marichely.Rollin.entity;

import javax.persistence.*;

@Entity
@Table(name = "Equipment")
public class Equipment {
    @Id
    @Column(name = "EquipmentID")
    private Integer id;
    @Column(name="Name")
    private String name;
    @Column(name ="State")
    private String state;
    @Column(name="Price_per_hour")
    private Integer pricePerHour;
    @Column(name="Price_per_day")
    private Integer pricePerDay;
    @Column(name="Currency")
    private String currency;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category")
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}