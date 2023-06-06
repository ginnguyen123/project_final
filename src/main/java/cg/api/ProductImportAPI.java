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
    public ResponseEntity<?> createNewProductImport(@RequestBody @Validated ProductImportCreReqDTO productImportCreReqDTO, BindingResult bindingResult) {

        new ProductImportCreReqDTO().validate(productImportCreReqDTO, bindingResult);
        productImportCreReqDTO.setId(null);


        EColor eColor = EColor.fromString(productImportCreReqDTO.getColor().toUpperCase());
        for (EColor e: EColor.values()) {
            assert eColor != null;
            if (eColor.getValue().equals(e.getValue())){
                ProductImport c = new ProductImport();
                c.setColor(eColor);
                break ;
            }else {
                throw new DataInputException("Not found color ");
            }
        }


        ESize size = ESize.parseESize(productImportCreReqDTO.getSize().toUpperCase());
        ESize eSize = ESize.parseESize(ESize.value());




        EProductStatus productStatus = EProductStatus.fromString(productImportCreReqDTO.getProductStatus().toUpperCase());
        for (EProductStatus ep: EProductStatus.values()) {
            assert productStatus != null;
            if (productStatus.getValue().equals(ep.getValue())){
                ProductImport p = new ProductImport();
                p.setProductStatus(productStatus);
                break ;
            }else {
                throw new DataInputException("Not found productStatus ");
            }
        }

        Optional<Product> productDTOOptional = productService.findById(productImportCreReqDTO.getProductDTO().getId());

        if (productDTOOptional.isEmpty()) {
            throw new ResourceNotFoundException("Not found this product");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }



        ProductImportCreResDTO productImportCreResDTO = productImportService.create(productDTOOptional.get().toProductDTO(),productImportCreReqDTO);

        return new ResponseEntity<>(productImportCreResDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAllProductImport(){
        List<ProductImport> productImportList = productImportService.findAll();
        return new ResponseEntity<>(productImportList, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@PathVariable Long id){
        ProductImport productImport = productImportService.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Not found this product to delete"));
        productImportService.delete(productImport);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
