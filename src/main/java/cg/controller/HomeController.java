package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {

//    @GetMapping("/")
//    public String showHomePage(){
//        return "index";
//    }
    @GetMapping("/home")
    public String showListProduct(){
        return "product/table-product";
    }
    @GetMapping("/create")
    public String showAddProduct(){
        return "product/form-add-product";
    }

}
