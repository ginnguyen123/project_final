package cg.service.product;

import cg.dto.productImport.ProductImportCreDTO;
import cg.model.category.Category;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

import java.util.List;

public interface IProductImportService extends IGeneralService<ProductImport , Long> {

    List<ProductImport> findAllProductImport();

    ProductImportCreDTO create( ProductImportCreDTO productImportCreDTO);
}
