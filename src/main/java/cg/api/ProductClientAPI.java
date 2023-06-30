package cg.api;

import cg.dto.product.client.ProductResClientDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<ProductResClientDTO> productResClientDTOS = products.stream().map(i->i.toProductResClientDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productResClientDTOS, HttpStatus.OK);
    }


}
