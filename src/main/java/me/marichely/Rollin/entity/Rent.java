package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Rent")
public class Rent {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RentID")
    @JsonProperty(value = "rentID")
    private Integer rentId;
    @Column(name = "dateFrom")
    @JsonProperty(value = "dateFrom")
    private Date dateFrom;
    @Column(name = "dateTo")
    @JsonProperty(value = "dateTo")
    private Date dateTo;
    @Column(name = "Status")
    @JsonProperty(value = "status")
    private Integer status;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "Bicycle")
    @JsonProperty(value = "bicycle")
    private Bicycle bicycle;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "Review")
    @JsonProperty(value = "review")
    private Review review;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "User")
    @JsonProperty(value = "user")
    private User user;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Equipment_with_Rent",
            joinColumns = {@JoinColumn(name = "Rent")},
            inverseJoinColumns = {@JoinColumn(name = "Equipment")}
    )
    private Set<Equipment> equipments = new HashSet<>();
    @JsonIgnore
    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
