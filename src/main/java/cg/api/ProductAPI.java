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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long idCategory) {
        List<Product> products = productService.findProductsByCategoryWithLimit(idCategory);
        List<ProductResDTO> productCreResDTOS = products.stream().map(item -> item.toVisitedAndRelatedProductResDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productCreResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> findProductById(@PathVariable Long id){
        if (id == null){
            throw new DataInputException("Product does not exist");
        }
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            ProductResDTO productResDTO = new ProductResDTO();
            return new ResponseEntity<>(productResDTO, HttpStatus.OK);
        }

        Product product = productOptional.get();

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
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
//        MultipartFile fileAvatar = productCreReqDTO.getFileAvatar();
//        List<MultipartFile> medias = productCreReqDTO.getMedias();

        if (!brandService.existsBrandById(brandId)){
            throw new DataInputException("The brand does not exist");
        }

        Brand brand = brandService.findById(brandId).get();

        if (!categoryService.existsById(categoryParentId)){
            throw new DataInputException("The category does not exist");
        }
        Category category = categoryService.findById(categoryParentId).get();

        if (code == null){
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
        product.setCategory(category);
        product.setBrand(brand);
        product.setProductAvatarList(list);
        product.setProductAvatar(avatarMedia);

        if (discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).isPresent()){
            Discount discount = discountService.findDiscountByIdAndDeletedIsFalse(productCreReqDTO.getDiscountId()).get();
            product.setDiscount(discount);
        }

        productService.save(product);

        ProductCreResDTO productCreResDTO = product.toProductCreResDTO();

        return new ResponseEntity<>(productCreResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @Validated ProductUpdaReqDTO productUpdaReqDTO,
                                    @RequestParam("updateMedias") List<MultipartFile> updateMedias,
                                    @RequestParam("updateAvatar") MultipartFile updateAvatar,
                                    @RequestParam("oldAvatar") MediaDTO oldAvatar,
                                    @RequestParam("oldMedia") List<MediaDTO> oldMedia) {

        if(productId == null){
            throw new DataInputException("Product is void");
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product isn't exist");
        }
        Product product = productUpdaReqDTO.toProduct();
        product.setId(productId);


        if (productUpdaReqDTO.getBrandId() == null){
            throw new DataInputException("Brand is void");
        }

        if (productUpdaReqDTO.getCategoryParentId() == null){
            throw new DataInputException("Category parent is void");
        }
        if (!categoryService.findById(productUpdaReqDTO.getCategoryParentId()).isPresent()){
            throw new DataInputException("Category isn't exist");
        }
        Optional<Category> newCategoryParent = categoryService.findById(productUpdaReqDTO.getCategoryParentId());
        Category newCategory = null;

        if (productUpdaReqDTO.getCategoryId() != null){
            Optional<Category> categoryChildOptional = categoryService.findById(productUpdaReqDTO.getCategoryId());
            if (categoryChildOptional.isPresent()){
                Category categoryChild = categoryChildOptional.get();
                categoryChild.setCategoryParent(newCategoryParent.get());
                newCategory = categoryChild;
            }
            else {
                newCategory = newCategoryParent.get();
            }
        }else {
            newCategory = newCategoryParent.get();
        }

        System.out.println(newCategory);
        Optional<Brand> brandOptional = brandService.findById(productUpdaReqDTO.getBrandId());

        if (!brandOptional.isPresent()){
            throw new DataInputException("Brand isn't exist");
        }

        if (updateAvatar == null && oldAvatar == null){
            throw new DataInputException("Avata is require");
        }

        if (updateAvatar != null){
            if (!updateAvatar.getContentType().equals("image/jpeg") && !updateAvatar.getContentType().equals("image/png")){
                throw new DataInputException("Only image files with .png or .jpeg");
            }
            Media avatarMedia = uploadMediaService.uploadImageAndSaveImage(updateAvatar, oldAvatar.toMedia());
            product.setProductAvatar(avatarMedia);
        }else {
            Media avatarMedia = oldAvatar.toMedia();
            product.setProductAvatar(avatarMedia);
        }

        if (updateMedias.size() == 0 || updateMedias == null){
            List<Media> mediaList = oldMedia.stream().map(i ->i.toMedia()).collect(Collectors.toList());
            product.setProductAvatarList(mediaList);
        }else {
            for (MultipartFile file : updateMedias){
                if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
                    updateMedias.remove(file);
                }
            }
            List<Media> medias = new ArrayList<>();
            List<Media> mediaList = uploadMediaService.uploadAllImageAndSaveAllImage(updateMedias, medias);
            if (oldMedia.size() != 0){
                List<Media> oldMedias = oldMedia.stream().map(i-> i.toMedia()).collect(Collectors.toList());
                mediaList.addAll(oldMedias);
            }
            product.setProductAvatarList(mediaList);
        }

        Brand newBrand = brandOptional.get();
        product.setBrand(newBrand);
        product.setCategory(newCategory);
        productService.save(product);
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
