package cg.controller;

import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.products.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product-import")
public class ProductImportController {

    @Autowired
    private IProductService productService;

    @GetMapping({"", "/"})
    public String showList(Model model){
        List<EColor> eColors = EColor.getEnumValues();
        List<ESize> eSizes = ESize.getEnumValues();
        List<Product> products = productService.findAllByDeletedFalse();
        model.addAttribute("color", eColors);
        model.addAttribute("size", eSizes);
        model.addAttribute("product", products);
        return "productImport/list/table-productImport";


    }

    @GetMapping("/create")
    public String showAddProduct(Model model){
        List<ESize> eSizes = ESize.getEnumValues();
        List<EColor> eColors = EColor.getEnumValues();
        List<Product> products = productService.findAllByDeletedFalse();
        model.addAttribute("size", eSizes);
        model.addAttribute("color", eColors);
        model.addAttribute("product", products);
        return "productImport/create/add-productImport";
    }

//    @GetMapping("/update")
//    public String showUpdateProduct(Model model){
//
//    }
}
