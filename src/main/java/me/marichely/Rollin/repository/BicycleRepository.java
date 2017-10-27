package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BicycleRepository extends JpaRepository<Bicycle,Integer> {
    public List<Bicycle> findByCategory(Category category);
}
