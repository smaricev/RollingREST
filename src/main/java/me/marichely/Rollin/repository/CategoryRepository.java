package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
