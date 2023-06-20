package cg.controller;


import cg.dto.product.ProductDTO;
import cg.model.discount.Discount;
import cg.model.product.Product;
import cg.service.category.ICategoryService;
import cg.service.discount.IDiscountService;
import cg.service.products.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IDiscountService discountService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping({"", "/"})
    public String showList(){
        return "product/list/table-product";
    }

    @GetMapping("/create")
    public String showAddProduct(){
        return "product/create/add-product";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProduct(@PathVariable Long id, Model model){
        Optional<Product> productOp = productService.findById(id);
        Map obj = new HashMap<>();

        if (!productOp.isPresent()){
            return "errors/pages-404";
        }
        Product product = productOp.get();
        ProductDTO productDTO = product.toProductDTO();
        obj.put("product", productDTO);
        model.addAttribute("data", obj);
        return "product/update/update-product";
    }
}
