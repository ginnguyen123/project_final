package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

public interface IProductImportService extends IGeneralService<ProductImport, Long> {

    ProductImportCreResDTO create(ProductImportCreReqDTO productImportCreReqDTO);

    ProductImportCreResDTO update(Product product,ProductImport productImport);

    Boolean existById(Long id);
}
