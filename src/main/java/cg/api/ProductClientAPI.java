package cg.api;

import cg.dto.product.client.FilterRes;
import cg.dto.product.client.ProductResClientDTO;
import cg.exception.DataInputException;
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

    @PostMapping("/category")
    private ResponseEntity<?> getAllProductByCategory(@RequestParam("id") Long id, Pageable pageable){
        return new ResponseEntity<>(productService.findAllByCategory(id,pageable),HttpStatus.OK);
    }
    @PostMapping("/filter/category")
    private ResponseEntity<?> getAllProductFilter(@RequestBody FilterRes filterRes, Pageable pageable){
        List<List<Long>> minMax = filterRes.getMinMax();
        Long id = filterRes.getId();
        if (minMax.size() != 0){
            for(int i = 0;  i < minMax.size(); i++){
                if (minMax.get(i).get(0) < 0 || minMax.get(i).get(1) < 0)
                        throw new DataInputException(AppConstant.INVALID_PRICE_FILTER_MESSAGE);
            }
        }
        return new ResponseEntity<>(productService.findAllByCategoryFilter(id,filterRes, pageable),HttpStatus.OK);
    }
    @PostMapping("/search")
    private ResponseEntity<?> getAllProductFilter(@RequestParam(defaultValue = "", value = "keyword") String keyWord, Pageable pageable){
        return new ResponseEntity<>(productService.findAllByKeyWordSearch(keyWord, pageable),HttpStatus.OK);
    }
}
