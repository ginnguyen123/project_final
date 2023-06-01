package cg.api;

import cg.dto.productImport.ProductImportCreDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;

import cg.service.product.IProductImportService;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-import")
public class ProductImportAPI {

    @Autowired
    private IProductImportService productImportService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping
    public ResponseEntity<?> createNewProductImport(@RequestBody @Validated ProductImportCreDTO productImportCreDTO) {
        ProductImportDTO productImportDTO = productImportService.create(productImportCreDTO);

        return new ResponseEntity<>(productImportDTO, HttpStatus.CREATED);
    }

//    @GetMapping()
//    public ResponseEntity<?> getAllProductImport(){
//        //List<ProductImport> productImportList = productImportService.findAll();
//        return new ResponseEntity<>(productImportList, HttpStatus.OK);
//    }


}
