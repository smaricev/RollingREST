package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
}
