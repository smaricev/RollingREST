package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Category;
import me.marichely.Rollin.entity.Equipment;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.CategoryRepository;
import me.marichely.Rollin.repository.EquipmentRepository;
import me.marichely.Rollin.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {
    private EquipmentRepository equipmentRepository;
    private CategoryRepository categoryRepository;
    private LoginService loginService;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository, CategoryRepository categoryRepository,LoginService loginService) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
        this.loginService = loginService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Equipment> getAllEquipment(@RequestHeader(value = "UserApiKey")String userApiKey){
        loginService.userCheck(userApiKey);
        List<Equipment> equipment =  equipmentRepository.findAll();
        if(equipment == null) throw new RestException(HttpStatus.NOT_FOUND,"No equipment found");
        return equipment;
    }

    @RequestMapping(value = "/{categoryId}",method = RequestMethod.GET)
    public @ResponseBody List<Equipment> getByCategory(@RequestHeader(value = "UserApiKey")String userApiKey,@PathVariable String categoryId, HttpServletResponse httpServletResponse){
        loginService.userCheck(userApiKey);
        if(!StringUtils.isNumeric(categoryId)){
            throw new RestException(HttpStatus.BAD_REQUEST,"CategoryID must be a number, you provided: " +categoryId);
        }
        Category category = categoryRepository.findOne(Integer.parseInt(categoryId));
        if(category == null) throw new RestException(HttpStatus.NOT_FOUND,"No CategoryID found in database for the provided id : "+categoryId);
        List<Equipment> equipment = equipmentRepository.findByCategory(category);
        if(equipment == null) throw new RestException(HttpStatus.NOT_FOUND,"No equipment found in detabase for the provided category id : "+categoryId);
        return equipmentRepository.findByCategory(category);
    }



}
