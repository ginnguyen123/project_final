package cg.api;

import cg.dto.productImport.*;

import cg.exception.ResourceNotFoundException;
import cg.model.product.ProductImport;
import cg.service.product.IProductImportService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.ProductImportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-import")
public class ProductImportAPI {

    @Autowired
    private IProductImportService productImportService;


    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/search")
    public ResponseEntity<?> pageableByKeywordAndDate(@RequestBody ProductImportRequest request, Pageable pageable) {
        Page<ProductImportDTO> productImportList = productImportService.pageableByKeywordAndDate(request, pageable);
        return new ResponseEntity<>(productImportList, HttpStatus.OK);
    }

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
        List<ProductImportDTO> productImportList = productImportService.findAllByDeletedIsFalse();
        return new ResponseEntity<>(productImportList, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Validated ProductImportUpReqDTO productImportUpReqDTO, BindingResult bindingResult ) throws IOException {
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

}
