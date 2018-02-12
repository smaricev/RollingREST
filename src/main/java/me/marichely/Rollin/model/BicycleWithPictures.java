package me.marichely.Rollin.model;

import me.marichely.Rollin.entity.Bicycle;

import java.util.List;

public class BicycleWithPictures {
    private Bicycle bicycle;
    private List<String> pictureIds;

    public BicycleWithPictures() {
    }

    public BicycleWithPictures(Bicycle bicycle, List<String> pictureIds) {
        this.bicycle = bicycle;
        this.pictureIds = pictureIds;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public List<String> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<String> pictureIds) {
        this.pictureIds = pictureIds;
    }
}
