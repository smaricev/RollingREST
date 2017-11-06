package me.marichely.Rollin;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.User;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BicycleRepository bicycleRepository;

    public UserTest(){
    }
    @Test
    @Ignore

    public void testUsers(){
        List<Bicycle> bicycles = bicycleRepository.findAll();
    }
}
