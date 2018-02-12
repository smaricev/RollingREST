package me.marichely.Rollin.controller;

import com.sun.org.apache.regexp.internal.RE;
import me.marichely.Rollin.entity.Equipment;
import me.marichely.Rollin.entity.Rent;
import me.marichely.Rollin.entity.User;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.EquipmentRepository;
import me.marichely.Rollin.repository.RentRepository;
import me.marichely.Rollin.repository.UserRepository;
import me.marichely.Rollin.services.LoginService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/rent")
public class RentController {

    private RentRepository rentRepository;
    private LoginService loginService;
    private UserRepository userRepository;
    private EquipmentRepository equipmentRepository;

    @Autowired
    public RentController(RentRepository rentRepository, LoginService loginService, UserRepository userRepository, EquipmentRepository equipmentRepository) {
        this.rentRepository = rentRepository;
        this.loginService = loginService;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/user" )
    @CrossOrigin
    public @ResponseBody
    List<Rent> getRentByID(@RequestHeader(value = "UserApiKey") String userApiKey) {
        loginService.userCheck(userApiKey);
        User user = userRepository.findByApiKey(userApiKey);
        List<Rent> rents = rentRepository.findByUser(user);
        return rents;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/object")
    @CrossOrigin
    public void removeRent(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestBody Rent rent, HttpServletResponse httpServletResponse) throws IOException {
        loginService.userCheck(userApiKey);
        if (rent != null) {
            rentRepository.delete(rent);
            httpServletResponse.setStatus(200);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @CrossOrigin
    public void removeRentWithId(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader(value = "rentid") String rentID, HttpServletResponse httpServletResponse) throws IOException {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isEmpty(rentID)) {
            if (!StringUtils.isNumeric(rentID)) {
                throw new RestException(HttpStatus.BAD_REQUEST, "Id must be a numeric");
            }
            rentRepository.delete(Integer.parseInt(rentID));
            httpServletResponse.setStatus(200);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin
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

    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    Set<Equipment> findEquipmentForRent(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader(value = "rentid") String rentID) {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isNumeric(rentID)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "Id is not provided or is not numeric");
        } else {
            Rent rent = rentRepository.findOne(Integer.parseInt(rentID));
            Set<Equipment> equipmentWIthRent = rent.getEquipments();
            return equipmentWIthRent;
        }
    }

    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody
    String setEquipmentForRent(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader(value = "rentid") String rentID, @RequestHeader(value = "equipmentid")String equipmentID) {
        loginService.userCheck(userApiKey);
        Rent rent = findRentWithId(rentID);
        Equipment equipment1 = findEquipmentWithId(equipmentID);
        if(rent.getEquipments().contains(equipment1)) throw  new RestException(HttpStatus.BAD_REQUEST,"Rent already contains the equipment you're trying to add");
        rent.getEquipments().add(equipment1);
        rentRepository.save(rent);
        return "Successfully added equipment to the rent";
    }

    @RequestMapping(value = "/equipment", method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody String deleteEquipmentForRent( @RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader(value = "rentid") String rentID, @RequestHeader(value = "equipmentid")String equipmentID){
        loginService.userCheck(userApiKey);
        Rent rent = findRentWithId(rentID);
        Equipment equipment1 = findEquipmentWithId(equipmentID);
        if(rent.getEquipments().remove(equipment1)){
            rentRepository.save(rent);
            return "Successfully removed the equipment for rent";
        }
        else return "Couldn't find the equipment for the selected rent";
    }

    private Equipment returnValidEquipment(Equipment equipment) {
        if (equipment.getId() != null) {
            Equipment eq = equipmentRepository.findOne(equipment.getId());
            return eq;
        }
        throw new RestException(HttpStatus.BAD_REQUEST, "Provided equipment is not valid");
    }

    private Rent findRentWithId(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "Id is not provided or is not numeric");
        } else {
            Rent rent = rentRepository.findOne(Integer.parseInt(id));
            if (rent == null)
                throw new RestException(HttpStatus.BAD_REQUEST, "rent couldn't be find for the provided id" + id);
            return rent;
        }
    }
    private Equipment findEquipmentWithId(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "Id is not provided or is not numeric");
        } else {
            Equipment equipment = equipmentRepository.findOne(Integer.parseInt(id));
            if (equipment == null)
                throw new RestException(HttpStatus.BAD_REQUEST, "rent couldn't be find for the provided id" + id);
            return equipment;
        }
    }
}
