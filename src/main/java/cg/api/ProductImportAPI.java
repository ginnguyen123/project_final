package cg.api;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.*;

import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.product.IProductImportService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product-import")
public class ProductImportAPI {

    @Autowired
    private IProductImportService productImportService;


    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;



    @PostMapping
    public ResponseEntity<?> createNewProductImport(
            @Validated ProductImportCreReqDTO productImportCreReqDTO,
            BindingResult bindingResult
    ) {


        new ProductImportCreReqDTO().validate(productImportCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
//        productImportCreReqDTO.setId(null);

        ProductImportCreResDTO productImportCreResDTO = productImportService.create(productImportCreReqDTO);

        return new ResponseEntity<>(productImportCreResDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductImportById(@PathVariable Long id) {
        ProductImportDTO productImportDTO = productImportService
                .getProductImportDTOByIdDeletedIsFalse(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Not found this product"));

        return new ResponseEntity<>(productImportDTO, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<?> getAllProductImport(){
        List<ProductImport> productImportList = productImportService.findAll();
        List<ProductImportCreResDTO> productImportCreResDTOS = productImportList
                .stream().map(item -> item.toProductImportCreResDTO()).collect(Collectors.toList());

        return new ResponseEntity<>(productImportCreResDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Validated ProductImportUpReqDTO productImportUpReqDTO, BindingResult bindingResult ) throws IOException {
        new ProductImportUpReqDTO().validate(productImportUpReqDTO,bindingResult);

            productImportUpReqDTO.setId(id);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
            ProductImportUpResDTO productImportUpResDTO = productImportService.update(productImportUpReqDTO);
        return new ResponseEntity<>(productImportUpResDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ProductImport productImport = productImportService.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Not found this product to delete"));
        productImportService.delete(productImport);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/size")
    public ResponseEntity<?> getAllSize() {
        List<ESize> size = ESize.getEnumValues();
        return new ResponseEntity<>(size, HttpStatus.OK);
    }








}
