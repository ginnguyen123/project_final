package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.*;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;


import java.util.Optional;


public interface IProductImportService extends IGeneralService<ProductImport, Long> {

    ProductImportCreResDTO create(ProductImportCreReqDTO productImportCreReqDTO);

    ProductImportUpResDTO update(ProductImportUpReqDTO productImportUpReqDTO);



    Boolean existById(Long id);

    Optional<ProductImportDTO> getProductImportDTOByIdDeletedIsFalse(Long id);


}
