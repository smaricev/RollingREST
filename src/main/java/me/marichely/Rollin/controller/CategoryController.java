package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Category;
import me.marichely.Rollin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository){
       this.categoryRepository = categoryRepository;
    }

    @GetMapping("/getall")
    public @ResponseBody List<Category> GetCategories(){
        return categoryRepository.findAll();
    }
}
