package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<Type,Integer> {
}
