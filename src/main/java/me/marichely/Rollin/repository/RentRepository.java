package me.marichely.Rollin.repository;

import me.marichely.Rollin.entity.Rent;
import me.marichely.Rollin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent,Integer> {
    List<Rent> findByUser(User user);
}
