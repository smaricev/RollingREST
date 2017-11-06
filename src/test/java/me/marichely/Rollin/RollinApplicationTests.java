package me.marichely.Rollin;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.User;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RollinApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BicycleRepository bicycleRepository;

	@Test
	@Ignore

	public void contextLoads() {
	}

	@Test
	@Ignore

	public void testUsers(){
		List<User> users = userRepository.findAll();
		List<Bicycle> bicycles = bicycleRepository.findAll();
	}

}
