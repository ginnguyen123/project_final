package cg.service.product;

import cg.dto.productImport.*;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;
import cg.utils.ProductImportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


public interface IProductImportService extends IGeneralService<ProductImport, Long> {

    ProductImportCreResDTO create(ProductImportCreReqDTO productImportCreReqDTO);

    ProductImportUpResDTO update(ProductImportUpReqDTO productImportUpReqDTO);

    List<ProductImportDTO> findAllByDeletedIsFalse( );
//    Page<ProductImportDTO> findAllByDeletedIsFalse(Pageable pageable);
    Boolean existById(Long id);

    Optional<ProductImportDTO> getProductImportDTOByIdDeletedIsFalse(Long id);

    Page<ProductImportDTO> pageableByKeywordAndDate(ProductImportRequest inputQuery, Pageable pageable);

//    Page<ProductImportDTO> findAllPagesByKeySearchAndDeletedIsFalse(String keySearch, Pageable pageable);


//    List<ProductImport> getProductByDeletedIsFalseAndProductNameLike(String key);


//    Page<ProductImportDTO> findProductByProductName(Product product, Pageable pageable);
}
