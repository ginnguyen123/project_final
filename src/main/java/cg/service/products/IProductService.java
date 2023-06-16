package cg.service.products;

import cg.dto.product.ProductCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.Product;
import cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IGeneralService<Product,Long> {
    List<Product> saveAll(List<Product> products);

//    List<ProductCreResDTO> findProductsByCategoryWithLimit( Long idCategory);

    List<Product> findProductsByCategoryWithLimit( Long idCategory);
    List<Product> findAllByDeletedFalse();


}
