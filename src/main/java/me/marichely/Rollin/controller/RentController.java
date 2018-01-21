package me.marichely.Rollin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.marichely.Rollin.entity.Rent;
import me.marichely.Rollin.entity.User;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.RentRepository;
import me.marichely.Rollin.repository.UserRepository;
import me.marichely.Rollin.services.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/rent")
public class RentController {

    private RentRepository rentRepository;
    private LoginService loginService;
    private UserRepository userRepository;

    @Autowired
    public RentController(RentRepository rentRepository, LoginService loginService, UserRepository userRepository) {
        this.rentRepository = rentRepository;
        this.loginService = loginService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user")
    public @ResponseBody
    List<Rent> getRentByID(@RequestHeader(value = "UserApiKey") String userApiKey) {
        loginService.userCheck(userApiKey);
        User user = userRepository.findByApiKey(userApiKey);
        List<Rent> rents = rentRepository.findByUser(user);
        return rents;
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/object")
    public void removeRent(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestBody Rent rent, HttpServletResponse httpServletResponse) throws IOException {
        loginService.userCheck(userApiKey);
        if (rent != null) {
            rentRepository.delete(rent);
            httpServletResponse.setStatus(200);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public void removeRentWithId(@RequestHeader(value = "UserApiKey") String userApiKey,@RequestHeader(value = "rentid")String rentID, HttpServletResponse httpServletResponse) throws IOException {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isEmpty(rentID)){
            if(!StringUtils.isNumeric(rentID)){
                throw new RestException(HttpStatus.BAD_REQUEST,"Id must be a numeric");
            }
            rentRepository.delete(Integer.parseInt(rentID));
            httpServletResponse.setStatus(200);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addNew(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestBody Rent rent, HttpServletResponse httpServletResponse) throws IOException {
        loginService.userCheck(userApiKey);
        if (rent != null) {
            if (rent.getRentId() != null && rentRepository.findOne(rent.getRentId()) != null) {
                throw new RestException(HttpStatus.FORBIDDEN, "you're trying to add a new item for an id that already exists");
            } else {
                rentRepository.save(rent);
                httpServletResponse.setStatus(200);
            }
        }
    }
}
