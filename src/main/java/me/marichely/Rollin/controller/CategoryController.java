package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Category;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.CategoryRepository;
import me.marichely.Rollin.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryRepository categoryRepository;
    private LoginService loginService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, LoginService loginService){
       this.categoryRepository = categoryRepository;
       this.loginService = loginService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Category> GetCategories(@RequestHeader(value = "UserApiKey")String userApiKey){
        loginService.userCheck(userApiKey);
        List<Category> categories =  categoryRepository.findAll();
        if(categories == null)throw new RestException(HttpStatus.NOT_FOUND,"No categories in repository");
        return categories;

    }
}
