package cg.api;

import cg.dto.product.*;
import cg.dto.product.client.ProductResClientDTO;
import cg.dto.productImport.ProductImportResDTO;
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
import cg.service.media.UploadMediaServiceImpl;
import cg.service.product.IProductImportService;
import cg.service.products.IProductService;
import cg.service.products.ProductService;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import cg.utils.UploadUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductAPI {

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

    @Autowired
    private IProductImportService productImportService;

    @GetMapping
    private ResponseEntity<?> getAllProductsDeleteFalse() {
        List<Product> products = productService.findAllByDeletedFalse();
        List<ProductDTO> productDTOS = products.stream().map(product -> product.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/discount-time")
    private ResponseEntity<?> getAllProductByDiscountTime(@RequestBody String string){
        Date date = appUtils.stringToDate(string);
        if (date == null){
            throw new DataInputException(AppConstant.INVALID_DATE_MESSAGE);
        }
        List<Product> products = productService.findAllByDiscountTime(date);
        List<ProductResClientDTO> productResClientDTOS = products.stream().map(i->i.toProductResClientDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productResClientDTOS,
                HttpStatus.OK);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long idCategory) {
        List<Product> products = productService.findProductsByCategoryWithLimit(idCategory);
        List<ProductResDTO> productCreResDTOS = products.stream().map(item -> item.toVisitedAndRelatedProductResDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productCreResDTOS, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id){
        if (id == null){
            throw new DataInputException("Product does not exist");
        }
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Product product = productOptional.get();
        List<ProductImportResDTO> productImports = productImportService.findQuantityProductImportBySizeAndColor(id);
        ProductResDTO productResDTO = product.toProductResDTO(productImports);

        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findProductByIdForDb(@PathVariable Long id){
        if (id == null){
            throw new DataInputException("Product does not exist");
        }
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Product product = productOptional.get();

        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }

    @GetMapping("/visited")
    public ResponseEntity<?> getVisitedProducts (@RequestParam("products") String productIds) {
        String[] arrStrIds = productIds.split("-");
        Set<String> uniqueStrIds = new HashSet<>();
        for (String id : arrStrIds) {
            uniqueStrIds.add(id);
        }

        List<Long> arrIds = new ArrayList<>();
        for (String id : uniqueStrIds) {
            Long productId = Long.parseLong(id);
            arrIds.add(productId);
        }

        List<ProductResDTO> productResDTOList = new ArrayList<>();

        for (Long item: arrIds) {
            Optional<Product> productOptional = productService.findById(item);
            if (!productOptional.isPresent()){
                throw new DataInputException("Product isn't exist");
            }
            Product currentProduct = productOptional.get();
            productResDTOList.add(currentProduct.toVisitedAndRelatedProductResDTO());
        }
        return new ResponseEntity<>(productResDTOList,HttpStatus.OK);
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
        if (!categoryChildOp.isPresent()){
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
        }else {
            product.setDiscount(null);
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

//        check kiểm tra có gửi lên id medias cũ
        if (!productUpdaReqDTO.getOldMedias().isEmpty()){
            String[] idOldMedias = productUpdaReqDTO.getOldMedias().split(",");
            List<Media> mediaList = new ArrayList<>();
            for (String str : idOldMedias){
                Media mda = uploadMediaService.findById(str).get();
                mediaList.add(mda);
            }
//            lọc ra danh sách cần xóa
            product.setProductAvatarList(mediaList);
            productService.save(product);
        }else {
            product.setProductAvatarList(null);
            productService.save(product);
        }


        return new ResponseEntity<>(product.toProductUpdaResDTO(), HttpStatus.OK);
    }

    @PatchMapping("/update-with-avatar/{id}")
    public ResponseEntity<?> updateWithAvatar(@PathVariable Long id,
                                              @Validated ProductUpdaReqDTO productUpdaReqDTO,
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

        //        check kiểm tra có gửi lên id medias cũ
        if (!productUpdaReqDTO.getOldMedias().isEmpty()){
            String[] idOldMedias = productUpdaReqDTO.getOldMedias().split(",");
            List<Media> mediaList = new ArrayList<>();
            for (String str : idOldMedias){
                Media mda = uploadMediaService.findById(str).get();
                mediaList.add(mda);
            }
//            lọc ra danh sách cần xóa
            product.setProductAvatarList(mediaList);
            productService.save(product);
        }else {
            product.setProductAvatarList(null);
            productService.save(product);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update-with-medias/{id}")
    public ResponseEntity<?> updateWithMedias(@PathVariable Long id,
                                              @Validated ProductUpdaReqDTO productUpdaReqDTO,
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

//        xử lý list hình cũ trước khi đưa xuống service để up hình
        Product product1 = productOptional.get();

        //        check kiểm tra có gửi lên id medias cũ
        if (!productUpdaReqDTO.getOldMedias().isEmpty()){
            String[] idOldMedias = productUpdaReqDTO.getOldMedias().split(",");
            List<Media> mediaList = new ArrayList<>();
            for (String str : idOldMedias){
                Media mda = uploadMediaService.findById(str).get();
                mediaList.add(mda);
            }
            product1.setProductAvatarList(mediaList);
            productService.save(product1);
        }else {
            product1.setProductAvatarList(null);
            productService.save(product1);
        }

        productUpdaReqDTO.setId(productOptional.get().getId());
        Product product = productService.updateWithMedias(productUpdaReqDTO, medias);

        return new ResponseEntity<>(product.toProductUpdaResDTO(), HttpStatus.OK);
    }

    @PatchMapping("/update-with-all/{id}")
    public ResponseEntity<?> updateWithAll(@PathVariable Long id,
                                           @Validated ProductUpdaReqDTO productUpdaReqDTO,
                                           @RequestParam("avatar") MultipartFile avatar,
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

//        xử lý list hình cũ trước khi đưa xuống service để up hình
        Product product1 = productOptional.get();

        //        check kiểm tra có gửi lên id medias cũ
        if (!productUpdaReqDTO.getOldMedias().isEmpty()){
            String[] idOldMedias = productUpdaReqDTO.getOldMedias().split(",");
            List<Media> mediaList = new ArrayList<>();
            for (String str : idOldMedias){
                Media mda = uploadMediaService.findById(str).get();
                mediaList.add(mda);
            }
            product1.setProductAvatarList(mediaList);
            productService.save(product1);
        }else {
            product1.setProductAvatarList(null);
            productService.save(product1);
        }

        productUpdaReqDTO.setId(productOptional.get().getId());
        Product product = productService.updateWithAvatarAndMedias(productUpdaReqDTO, avatar ,medias);

        return new ResponseEntity<>(product.toProductUpdaResDTO(), HttpStatus.OK);
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

    @PostMapping("/pagination")
    private ResponseEntity<?> getProductsWithSort(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable){
        search = "%" + search + "%";
        return new ResponseEntity<>(productService.findProductWithPaginationAndSortAndSearch(search, pageable),HttpStatus.OK);
    }
}
