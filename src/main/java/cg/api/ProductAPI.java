package cg.api;

import cg.dto.product.ProductCreReqDTO;
import cg.dto.product.ProductCreResDTO;
import cg.dto.product.ProductDTO;
import cg.exception.DataInputException;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.service.brand.IBrandService;
import cg.service.category.ICategoryService;
import cg.service.media.IUploadMediaService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    private UploadUtils uploadUtils;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOS = products.stream().map(item -> item.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }

    @GetMapping("/get")
    private ResponseEntity<?> getAllProductsDeleteFalse() {
        List<Product> products = productService.findAllByDeletedFalse();
        List<ProductDTO> productDTOS = products.stream().map(item -> item.toProductDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/category={idCategory}")
    private ResponseEntity<?> getProductsByCategory(@PathVariable Long idCategory) {
        List<Product> products = productService.findProductsByCategoryWithLimit(idCategory);
        List<ProductCreResDTO> productCreResDTOS = products.stream().map(item -> item.toProductCreResDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(productCreResDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@Validated ProductCreReqDTO productCreReqDTO, BindingResult bindingResult){

        new ProductCreReqDTO().validate(productCreReqDTO, bindingResult);
        productCreReqDTO.setId(null);
        MultipartFile avatar = productCreReqDTO.getAvatar();
        String code = productCreReqDTO.getCode();
        Long brandId = productCreReqDTO.getBrandId();
        Long categoryId = productCreReqDTO.getCategoryId();
        if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
        /* Check brand, catagory */

        if (!brandService.existsBrandById(brandId)){
            throw new DataInputException("The brand does not exist");
        }

        Brand brand = brandService.findById(brandId).get();

        if (!categoryService.existsById(categoryId)){
            throw new DataInputException("The category does not exist");
        }
        Category category = categoryService.findById(categoryId).get();

        if (code.isEmpty() || code == null){
            code = "";
            Long numCode = System.currentTimeMillis()/1000;
            String[] brandCodes = brand.getName().split("", 3);
            String[] categoryCodes = category.getName().split("",3);
            for (var i = 0; i < brandCodes.length - 1; i++){
                code = code + brandCodes[i];
            }
//            for (var i = 0; i < categoryCodes.length - 1; i++){
//                code = code + categoryCodes[i];
//            }
            code = code + numCode.toString();
            productCreReqDTO.setCode(code);
        }

        if (avatar == null){
            throw new DataInputException("The avatar is required");
        }

        Media media = new Media();
        media.setProductImport(null);
        uploadMediaService.save(media);

        try{
            Map uploadResult = uploadMediaService.uploadImage(avatar, uploadUtils.buildImageUploadParams(media));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");
            media.setFileName(media.getId() + "." + fileFormat);
            media.setFileUrl(fileUrl);
            media.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
            media.setCloudId(media.getFileFolder() + "/" + media.getId());
            uploadMediaService.save(media);
        }
        catch (IOException e){
            e.printStackTrace();
            throw new DataInputException("Upload fail");
        }

        Product product = productCreReqDTO.toProduct();
        product.setCategory(category);
        product.setBrand(brand);
        product.setProductAvatar(media);

        productService.save(product);

        ProductCreResDTO productCreResDTO = product.toProductCreResDTO();

        return new ResponseEntity<>(productCreResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
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

}
