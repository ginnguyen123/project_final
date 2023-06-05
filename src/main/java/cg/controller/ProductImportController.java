package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product-import")
public class ProductImportController {
    @GetMapping({"", "/"})
    public String showList(){
        return "productImport/list/table-productImport";
    }

    @GetMapping("/create")
    public String showAddProduct(){
        return "productImport/create/add-productImport";
    }
}
