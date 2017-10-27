package me.marichely.Rollin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.Category;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/bicycle")
public class BicycleController {
    private BicycleRepository bicycleRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    BicycleController(BicycleRepository bicycleRepository,CategoryRepository categoryRepository){
        this.bicycleRepository = bicycleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/getall")
    public @ResponseBody List<Bicycle> getAllBicycles(){
        return bicycleRepository.findAll();
    }
    @GetMapping("/cat{category}")
    public @ResponseBody List<Bicycle> getByCategory(@PathVariable String category){
        return bicycleRepository.findByCategory(categoryRepository.findOne(Integer.parseInt(category)));
    }
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBicycleStatus(HttpServletRequest request,HttpServletResponse response){
        String bicycleID = request.getParameter("bicycleid");
        Integer status = Integer.parseInt(request.getParameter("status"));
        if(status>1) {
            response.setStatus(403);
            return;
        }
        Bicycle bicycle = bicycleRepository.findOne(Integer.parseInt(bicycleID));
        bicycle.setState(status);
        bicycleRepository.save(bicycle);
    }

}
