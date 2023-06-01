package cg.service.product;

import cg.dto.productImport.ProductImportCreDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

public interface IProductImportService extends IGeneralService<Long , ProductImport> {

    ProductImportDTO create(ProductImportCreDTO productImportCreDTO);
}
