package cg.api;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;

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
            @RequestBody @Validated ProductImportCreReqDTO productImportCreReqDTO,
            BindingResult bindingResult
    ) {

        //validate vai bua chuyen qua service sau
        //ko viet nghiep vu o tang RestController
        new ProductImportCreReqDTO().validate(productImportCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
//        productImportCreReqDTO.setId(null);

        ProductImportCreResDTO productImportCreResDTO = productImportService.create(productImportCreReqDTO);

        return new ResponseEntity<>(productImportCreResDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAllProductImport(){
        List<ProductImport> productImportList = productImportService.findAll();
        List<ProductImportCreResDTO> productImportCreResDTOS = productImportList
                .stream().map(item -> item.toProductImportCreResDTO()).collect(Collectors.toList());

        return new ResponseEntity<>(productImportCreResDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductImportCreReqDTO productImportCreReqDTO) {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ProductImport productImport = productImportService.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Not found this product to delete"));
        productImportService.delete(productImport);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
