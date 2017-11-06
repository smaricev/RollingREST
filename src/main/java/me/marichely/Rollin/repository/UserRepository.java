package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    public User findByApiKey(String apiKey);


}
