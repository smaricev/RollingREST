package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.BicyclePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicyclePictureRepository extends JpaRepository<BicyclePicture,String> {

}
