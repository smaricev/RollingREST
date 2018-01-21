package me.marichely.Rollin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Table(name = "Reviews")
@Entity
public class Review {

    @Column(name = "Description")
    @JsonProperty(value = "description")
    private String description;

    @Column(name="Stars")
    @JsonProperty(value = "stars")
    private Integer stars;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ReviewsID")
    @JsonProperty(value = "reviewsID")
    private Integer reviewID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

}
