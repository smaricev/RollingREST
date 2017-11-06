package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepositorys extends JpaRepository<Type,Integer> {
}
