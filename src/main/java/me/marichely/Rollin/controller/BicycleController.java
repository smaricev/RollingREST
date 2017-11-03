package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public void updateBicycleStatus(@RequestParam("bicycleid") String bicycleID,@RequestParam("status") String status,HttpServletResponse httpServletResponse){
        Integer statusint = Integer.parseInt(status);
        if(statusint>2) {
            httpServletResponse.setStatus(403);
            return;
        }
        Bicycle bicycle = bicycleRepository.findOne(Integer.parseInt(bicycleID));
        bicycle.setState(statusint);
        bicycleRepository.save(bicycle);
        httpServletResponse.setStatus(200);
    }


}
