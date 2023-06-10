package cg.service.products;

import cg.dto.product.ProductCreResDTO;
import cg.model.product.Product;
import cg.service.IGeneralService;

import java.awt.print.Pageable;
import java.util.List;

public interface IProductService extends IGeneralService<Product,Long> {
    List<Product> saveAll(List<Product> products);

//    List<ProductCreResDTO> findProductsByCategoryWithLimit( Long idCategory);

    List<Product> findProductsByCategoryWithLimit( Long idCategory);
    List<Product> findAllByDeletedFalse();
}
