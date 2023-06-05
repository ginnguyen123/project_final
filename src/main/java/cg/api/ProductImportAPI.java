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

        EnumSet<EColor> allColor = EnumSet.allOf(EColor.class);
        List<EColor> listColor = new ArrayList<>( allColor.size());
        for (EColor s : allColor) {
            listColor.add( s);
        }
        EColor eColor = EColor.fromString(productImportCreReqDTO.getColor().toUpperCase());
        for (EColor e: listColor) {
            if (eColor.getValue().equals(e.getValue())){
                ProductImport productImport = new ProductImport();
                productImport.setColor(eColor);
                break ;
            }else {
                throw new DataInputException("Not found color ");
            }
        }

        EnumSet<ESize> allSize = EnumSet.allOf(ESize.class);
        List<ESize> listSize = new ArrayList<>( allSize.size());
        for (ESize s : allSize) {
            listSize.add( s);
        }
        ESize size = ESize.fromString(productImportCreReqDTO.getSize().toUpperCase());
        for (ESize e: listSize) {
            if (size.getValue().equals(e.getValue())){
                ProductImport productImport = new ProductImport();
                productImport.setSize(size);
                break ;
            }else {
                throw new DataInputException("Not found size ");
            }
        }

        EnumSet<EProductStatus> all = EnumSet.allOf(EProductStatus.class);
        List<EProductStatus> listProductStatus = new ArrayList<>( all.size());
        for (EProductStatus s : all) {
            listProductStatus.add( s);
        }
        EProductStatus productStatus = EProductStatus.fromString(productImportCreReqDTO.getProductStatus().toUpperCase());
        for (EProductStatus e: listProductStatus) {
            if (productStatus.getValue().equals(e.getValue())){
                ProductImport productImport = new ProductImport();
                productImport.setProductStatus(productStatus);
                break ;
            }else {
                throw new DataInputException("Not found productStatus ");
            }
        }

        Optional<Product> productDTOOptional = productService.findById(productImportCreReqDTO.getProductDTO().getId());

        if (!productDTOOptional.isPresent()) {
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
