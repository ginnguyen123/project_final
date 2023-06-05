package cg.service.product;

import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

public interface IProductImportService extends IGeneralService<ProductImport, Long> {

    ProductImportCreResDTO create(ProductImportCreReqDTO productImportCreReqDTO);

    ProductImportCreResDTO update(ProductImport productImport);

    Boolean existById(Long id);
}
