package cg.api;


import cg.dto.productImport.ProductImportCreDTO;

import cg.model.product.ProductImport;

import cg.service.product.IProductImportService;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductImportAPI {

    @Autowired
    private IProductImportService productImportService;

    @Autowired
    private AppUtils appUtils;





    @GetMapping()
    public ResponseEntity<?> getAllProductImport(){
        List<ProductImport> productImportList = productImportService.findAllProductImport();
        return new ResponseEntity<>(productImportList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createProductImport(@Validated ProductImportCreDTO productImportCreDTO , BindingResult bindingResult){
        new ProductImportCreDTO().validate(productImportCreDTO,bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        ProductImportCreDTO importCreDTO = productImportService.create(productImportCreDTO);

        return new ResponseEntity<>(importCreDTO , HttpStatus.CREATED);

    }

}
