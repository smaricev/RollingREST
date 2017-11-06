package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.CategoryRepository;
import me.marichely.Rollin.services.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/bicycle")
public class BicycleController {
    private BicycleRepository bicycleRepository;
    private CategoryRepository categoryRepository;
    private LoginService loginService;

    @Autowired
    BicycleController(BicycleRepository bicycleRepository,CategoryRepository categoryRepository,LoginService loginService){
        this.bicycleRepository = bicycleRepository;
        this.categoryRepository = categoryRepository;
        this.loginService=loginService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Bicycle> getAllBicycles(@RequestHeader(value = "UserApiKey")String userApiKey){
        loginService.userCheck(userApiKey);
        List<Bicycle> bicycles = bicycleRepository.findAll();
        if(bicycles==null)throw new RestException(HttpStatus.NOT_FOUND,"Bicycle repositoy error contact admin");
        if(bicycles.isEmpty())throw new RestException(HttpStatus.NOT_FOUND,"Empty Bicycle repositoy");

        return bicycles;
    }

    @GetMapping("/cat{category}")
    public @ResponseBody List<Bicycle> getByCategory(@RequestHeader(value = "UserApiKey")String userApiKey,@PathVariable String category){
        loginService.userCheck(userApiKey);
        List<Bicycle> bicycles = bicycleRepository.findByCategory(categoryRepository.findOne(Integer.parseInt(category)));
        if(bicycles==null)throw new RestException(HttpStatus.NOT_FOUND,"Bicycle/Category Repository error contact admin");
        if(bicycles.isEmpty())throw new RestException(HttpStatus.NOT_FOUND,"Empty Bicycle repositoy for the provided category" + category);
        return bicycles;
    }

    @PostMapping("/update")
    public void updateBicycleStatus(@RequestHeader(value = "UserApiKey")String userApiKey,@RequestParam("bicycleid") String bicycleID,@RequestParam("status") String status,HttpServletResponse httpServletResponse){
        loginService.userCheck(userApiKey);
        if(!StringUtils.isNumeric(status))throw new RestException(HttpStatus.BAD_REQUEST,"Status must be a number (0, 1 or 2)");
        if(!StringUtils.isNumeric(bicycleID))throw new RestException(HttpStatus.BAD_REQUEST,"bicycleid must be a number");
        Integer statusint = Integer.parseInt(status);
        if(statusint>2) {
           throw new RestException(HttpStatus.FORBIDDEN,"Status must be (0 ,1 or 2) and no other numeric value");
        }
        Bicycle bicycle = bicycleRepository.findOne(Integer.parseInt(bicycleID));
        if(bicycle == null) throw new RestException(HttpStatus.NOT_FOUND,"Couldn't find the bicycle for the provided ID" + bicycleID);
        bicycle.setState(statusint);
        bicycleRepository.save(bicycle);
        httpServletResponse.setStatus(200);
    }


}
