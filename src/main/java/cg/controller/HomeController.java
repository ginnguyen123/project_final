package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }
    @GetMapping("/home")
    public String showListProduct(){
        return "index";
    }
//    @GetMapping("/create")
//    public String showAddProduct(){
//        return "product/add-product";
//    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

}
