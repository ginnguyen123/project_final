package cg.api;

import cg.dto.product.client.ProductResClientDTO;
import cg.exception.DataInputException;
import cg.model.category.Category;
import cg.model.product.Product;
import cg.service.brand.IBrandService;
import cg.service.category.ICategoryService;
import cg.service.discount.IDiscountService;
import cg.service.media.IUploadMediaService;
import cg.service.products.IProductService;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import cg.utils.UploadUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/api/client/products")
public class ProductClientAPI {
    private final IProductService productService;
    private final IUploadMediaService uploadMediaService;
    @Autowired
    private IBrandService brandService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IDiscountService discountService;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AppConstant appConstant;
    @GetMapping("/discount-time")
    private ResponseEntity<?> getAllProductByDiscountTime(){
        LocalDate date = LocalDate.now();
        List<Product> products = productService.findAllByDiscountTime(date);
        if (products.size() != 0){
            List<ProductResClientDTO> productResClientDTOS = products.stream().map(i->i.toProductResClientDTO()).collect(Collectors.toList());
            return new ResponseEntity<>(productResClientDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/category/{id}")
    private ResponseEntity<?> getAllProductByCategory(@PathVariable("id") Long id){
        productService.findAllByCategory(id);
        return new ResponseEntity<>(productService.findAllByCategory(id),HttpStatus.OK);
    }
}
