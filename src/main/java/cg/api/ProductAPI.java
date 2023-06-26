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
import cg.service.discount.IDiscountService;
import cg.service.media.IUploadMediaService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<?> getAllProductsDeleteFalse() {
        List<Product> products = productService.findAllByDeletedFalse();
        List<ProductDTO> productDTOS = products.stream().map(product -> product.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
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
                                     @RequestParam("avatar") MultipartFile fileAvatar,
                                     @RequestParam("medias") List<MultipartFile> medias){

        productCreReqDTO.setId(null);
        String code = productCreReqDTO.getCode();
        Long brandId = productCreReqDTO.getBrandId();
        Long categoryParentId = productCreReqDTO.getCategoryParentId();
        Long categoryChildId = productCreReqDTO.getCategoryId();

        if (!brandService.existsBrandById(brandId)){
            throw new DataInputException("The brand does not exist");
        }

        Brand brand = brandService.findById(brandId).get();

        //        check category child
        Optional<Category> categoryChildOp = categoryService.findById(categoryChildId);
        if (categoryChildOp.isPresent()){
            throw new DataInputException("The category child does not exist");
        }

        if (!categoryService.existsById(categoryParentId)){
            throw new DataInputException("The category does not exist");
        }

        Category category = categoryChildOp.get();

        //Tạo mã code sp theo tên brand
        if (code == null){
            code = "";
            Long numCode = System.currentTimeMillis()/1000;
            String[] brandCodes = brand.getName().split("", 3);
            for (var i = 0; i < brandCodes.length - 1; i++){
                code = code + brandCodes[i];
            }
            code = code + numCode.toString();
            productCreReqDTO.setCode(code);
        }

        if (fileAvatar == null){
            throw new DataInputException("The avatar is require");
        }

        if (!fileAvatar.getContentType().equals("image/jpeg") && !fileAvatar.getContentType().equals("image/png")){
            throw new DataInputException("Only image files with .png or .jpeg");
        }

        Media avatarMedia = new Media();
        avatarMedia = uploadMediaService.save(avatarMedia);
        avatarMedia = uploadMediaService.uploadImageAndSaveImage(fileAvatar, avatarMedia);

        List<Media> list = new ArrayList<>();
        if (medias.size() != 1 && medias.size() != 0){
            for (MultipartFile file : medias){
                if (file.getContentType() == null){
                    continue;
                }
                else {
                    if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
                        throw new DataInputException("Only image files with .png or .jpeg");
                    }
                }
            }
            list = uploadMediaService.uploadAllImageAndSaveAllImage(medias, list);
        }

        Product product = productCreReqDTO.toProduct();
        product.setBrand(brand);
        product.setProductAvatarList(list);
        product.setProductAvatar(avatarMedia);
        product.setCategory(category);

        if (discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).isPresent()){
            Discount discount = discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).get();
            product.setDiscount(discount);
        }

        productService.save(product);

        ProductCreResDTO productCreResDTO = product.toProductCreResDTO();

        return new ResponseEntity<>(productCreResDTO, HttpStatus.CREATED);
    }


    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId,
                                    @Validated ProductUpdaReqDTO productUpdaReqDTO) {

        if(productId == null){
            throw new DataInputException("Product's value void");
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product isn't exist");
        }

        Long brandId = productUpdaReqDTO.getBrandId();
        Optional<Brand> brandOp = brandService.findById(brandId);
        if (!brandOp.isPresent()){
            throw new DataInputException("Brand isn't exist");
        }

        Long categoryId = productUpdaReqDTO.getCategoryId();
        Optional<Category> categoryOp = categoryService.findById(categoryId);
        if (!categoryOp.isPresent()){
            throw new DataInputException("Category isn't exist");
        }

        if (productUpdaReqDTO.getDiscountId() != null){
            Long discountId = productUpdaReqDTO.getDiscountId();
            Optional<Discount> discountOp = discountService.findById(discountId);
            if (!discountOp.isPresent()){
                throw new DataInputException("Discount isn't exist");
            }
        }

        productUpdaReqDTO.setId(productOptional.get().getId());
        Product product = productService.update(productUpdaReqDTO);



        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update-with-avatar/{id}")
    public ResponseEntity<?> updateWithAvatar(@PathVariable Long id,
                                              @RequestBody @Validated ProductUpdaReqDTO productUpdaReqDTO,
                                              @RequestParam("avatar") MultipartFile avatar){
        if(id == null){
            throw new DataInputException("Product's value void");
        }

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product isn't exist");
        }

        Long brandId = productUpdaReqDTO.getBrandId();
        Optional<Brand> brandOp = brandService.findById(brandId);
        if (!brandOp.isPresent()){
            throw new DataInputException("Brand isn't exist");
        }

        Long categoryId = productUpdaReqDTO.getCategoryId();
        Optional<Category> categoryOp = categoryService.findById(categoryId);
        if (!categoryOp.isPresent()){
            throw new DataInputException("Category isn't exist");
        }

        if (productUpdaReqDTO.getDiscountId() != null){
            Long discountId = productUpdaReqDTO.getDiscountId();
            Optional<Discount> discountOp = discountService.findById(discountId);
            if (!discountOp.isPresent()){
                throw new DataInputException("Discount isn't exist");
            }
        }

        productUpdaReqDTO.setId(productOptional.get().getId());
        Product product = productService.updateWithAvatar(productUpdaReqDTO, avatar);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update-with-medias/{id}")
    public ResponseEntity<?> updateWithMedias(@PathVariable Long id,
                                              @RequestBody @Validated ProductUpdaReqDTO productUpdaReqDTO,
                                              @RequestParam("medias") List<MultipartFile> medias){
        if(id == null){
            throw new DataInputException("Product's value void");
        }

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product isn't exist");
        }

        Long brandId = productUpdaReqDTO.getBrandId();
        Optional<Brand> brandOp = brandService.findById(brandId);
        if (!brandOp.isPresent()){
            throw new DataInputException("Brand isn't exist");
        }

        Long categoryId = productUpdaReqDTO.getCategoryId();
        Optional<Category> categoryOp = categoryService.findById(categoryId);
        if (!categoryOp.isPresent()){
            throw new DataInputException("Category isn't exist");
        }

        if (productUpdaReqDTO.getDiscountId() != null){
            Long discountId = productUpdaReqDTO.getDiscountId();
            Optional<Discount> discountOp = discountService.findById(discountId);
            if (!discountOp.isPresent()){
                throw new DataInputException("Discount isn't exist");
            }
        }

        productUpdaReqDTO.setId(productOptional.get().getId());
        Product product = productService.updateWithMedias(productUpdaReqDTO, medias);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update-with-all/{id}")
    public ResponseEntity<?> updateWithAll(@PathVariable Long id,
                                           @RequestBody @Validated ProductUpdaReqDTO productUpdaReqDTO,
                                           @RequestParam("avatar") MultipartFile avatar,
                                           @RequestParam("medias") List<MultipartFile> medias){



        return new ResponseEntity<>(HttpStatus.OK);
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

//    @PostMapping("/sort/{field}")
//    private ResponseEntity<?> getProductsWithSort(@PathVariable String field){
//        List<Product> products = productService.findProductWithSorting(field);
//        List<ProductDTO> productDTOS = products.stream().map(i -> i.toProductDTO()).collect(Collectors.toList());
//        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
//    }

    @PostMapping("/pagination")
    private ResponseEntity<?> getProductsWithSort(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable){
        search = "%" + search + "%";
        return new ResponseEntity<>(productService.findProductWithPaginationAndSortAndSearch(search, pageable),HttpStatus.OK);
    }


}
