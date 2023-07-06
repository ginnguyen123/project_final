package cg.service.product;

import cg.dto.productImport.*;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
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

    List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(Long productId);

    List<EColor> getAllColorByCategory(Long id);

    List<ESize> getAllSizeByCategory(Long id);

}
