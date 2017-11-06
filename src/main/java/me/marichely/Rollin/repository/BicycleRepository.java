package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle,Integer> {
    public List<Bicycle> findByCategory(Category category);
}
