package cg.service.products;

import cg.dto.product.*;
import cg.model.product.Product;
import cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


public interface IProductService extends IGeneralService<Product,Long> {
    List<Product> saveAll(List<Product> products);

//    List<ProductCreResDTO> findProductsByCategoryWithLimit( Long idCategory);

    List<Product> findProductsByCategoryWithLimit( Long idCategory);
    List<Product> findAllByDeletedFalse();
    List<Product> findProductWithSorting(String field);
    ProductUpdaResDTO update(ProductUpdaReqDTO productUpdaReqDTO);
    Page<ProductListResponse> findProductWithPaginationAndSortAndSearch(String search, Pageable pageable);

}
