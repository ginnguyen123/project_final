package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

public interface IProductImportService extends IGeneralService<ProductImport, Long> {

    ProductImportCreResDTO create(ProductDTO productDTO, ProductImportCreReqDTO productImportCreReqDTO);

    ProductImportCreResDTO update(ProductDTO productDTO,ProductImport productImport);

    Boolean existById(Long id);
}
