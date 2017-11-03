package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Category;
import me.marichely.Rollin.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
    List<Equipment> findByCategory(Category category);
}
