package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping({"", "/"})
    public String showList(){
        return "product/list/table-product";
    }

    @GetMapping("/create")
    public String showAddProduct(){
        return "product/create/add-product";
    }


}
