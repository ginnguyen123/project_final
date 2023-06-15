package cg.api;

import cg.dto.media.MediaDTO;
import cg.dto.product.*;
import cg.exception.DataInputException;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.service.brand.IBrandService;
import cg.service.category.ICategoryService;
import cg.service.discount.DiscountServiceImpl;
import cg.service.discount.IDiscountService;
import cg.service.media.IUploadMediaService;
import cg.service.media.UploadMediaServiceImpl;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.UploadUtils;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private IProductService productService;

    @Autowired
    private IUploadMediaService uploadMediaService;

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

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOS = products.stream().map(item -> item.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }

    @GetMapping("/delete-list")
    private ResponseEntity<?> getAllProductsDeleteFalse() {
        List<Product> products = productService.findAllByDeletedFalse();
        List<ProductDTO> productDTOS = products.stream().map(item -> item.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/category={idCategory}")
    private ResponseEntity<?> getProductsByCategory(@PathVariable Long idCategory) {
        List<Product> products = productService.findProductsByCategoryWithLimit(idCategory);
        List<ProductCreResDTO> productCreResDTOS = products.stream().map(item -> item.toProductCreResDTOByCategory()).collect(Collectors.toList());
        return new ResponseEntity<>(productCreResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findProductById(@PathVariable Long id){
        if (id == null){
            throw new DataInputException("Product does not exist");
        }
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            ProductDTO productDTO = new ProductDTO();
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }

        Product product = productOptional.get();

        ProductDTO productDTO = product.toProductDTO();

        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@Validated ProductCreReqDTO productCreReqDTO,
                                     @RequestParam("medias") List<MultipartFile> medias){

        productCreReqDTO.setId(null);
        String code = productCreReqDTO.getCode();
        Long brandId = productCreReqDTO.getBrandId();
        Long categoryParentId = productCreReqDTO.getCategoryParentId();

        if (!brandService.existsBrandById(brandId)){
            throw new DataInputException("The brand does not exist");
        }

        Brand brand = brandService.findById(brandId).get();

        if (!categoryService.existsById(categoryParentId)){
            throw new DataInputException("The category does not exist");
        }
        Category category = categoryService.findById(categoryParentId).get();

        if (code.isEmpty() || code == null){
            code = "";
            Long numCode = System.currentTimeMillis()/1000;
            String[] brandCodes = brand.getName().split("", 3);
            String[] categoryCodes = category.getName().split("",3);
            for (var i = 0; i < brandCodes.length - 1; i++){
                code = code + brandCodes[i];
            }
            code = code + numCode.toString();
            productCreReqDTO.setCode(code);
        }

        if (medias.size() == 0){
            throw new DataInputException("Require at least 1 picture");
        }

        for (MultipartFile file : medias){
            System.out.println(file.getContentType());
            if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
                throw new DataInputException("Only image files with .png or .jpeg");
            }
        }

        List<Media> list = new ArrayList<>();

        list = uploadMediaService.uploadAllImageAndSaveAllImage(medias, list);

        Product product = productCreReqDTO.toProduct();
        product.setCategory(category);
        product.setBrand(brand);
        product.setProductAvatarList(list);
        product.setProductAvatar(list.get(0));

        if (discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).isPresent()){
            Discount discount = discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).get();
            List<Product> products = discount.getProducts();
            products.add(product);
            discount.setProducts(products);
            discountService.save(discount);
        }

        productService.save(product);

        ProductCreResDTO productCreResDTO = product.toProductCreResDTO();

        return new ResponseEntity<>(productCreResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, ProductUpdaReqDTO productUpdaReqDTO,
                                    @RequestParam("medias") List<MultipartFile> medias,
                                    @RequestParam("avatar") MultipartFile avatar) {
        if (productId == null){
            throw new DataInputException("Invalid id");
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product isn't exist");
        }

        if (avatar == null){
            throw new DataInputException("Avatar required!");
        }
        medias.add(avatar);
//        avatar nằm vị trí cuối
        Product product = productOptional.get();

        List<Media> mediasUpdate = uploadMediaService.updateAllImage(medias,product);


        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<?> delete(@PathVariable Long productID) {
        Optional<Product> productOptional = productService.findById(productID);
        if (!productOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }
        Product product = productOptional.get();
        productService.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sort/{field}")
    private ResponseEntity<?> getProductsWithSort(@PathVariable String field){
        List<Product> products = productService.findProductWithSorting(field);
        List<ProductDTO> productDTOS = products.stream().map(i -> i.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping("/pagination")
    private ResponseEntity<?> getProductsWithSort(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable){
        search = "%" + search + "%";
        return new ResponseEntity<>(productService.findProductWithPaginationAndSortAndSearch(search, pageable),HttpStatus.OK);
    }

}
