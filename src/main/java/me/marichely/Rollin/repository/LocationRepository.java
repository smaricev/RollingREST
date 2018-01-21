package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
    List<Location> findByBicycle(Bicycle bicycle);
}
