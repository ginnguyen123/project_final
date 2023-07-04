package cg.repository;

import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportResDTO;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.utils.ProductImportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImportRepository extends JpaRepository<ProductImport, Long> {

    List<ProductImport> findAllByProductId(Long productId);


    @Query("SELECT NEW cg.dto.productImport.ProductImportDTO (" +
            "pi.id, " +
            "pi.size, " +
            "pi.color, " +
            "pi.code, " +
            "pi.date_added, " +
            "pi.price, " +
            "pi.quantity, " +
            "pi.productStatus, " +
            "pi.product " +
            ") " +
            "FROM ProductImport AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<ProductImportDTO> getProductImportDTOByIdDeletedIsFalse(Long id);

    @Query("SELECT NEW cg.dto.productImport.ProductImportDTO (" +
            "pi.id, " +
            "pi.size, " +
            "pi.color, " +
            "pi.code, " +
            "pi.date_added, " +
            "pi.price, " +
            "pi.quantity, " +
            "pi.productStatus, " +
            "pi.product" +
            ") " +
            "FROM ProductImport AS pi " +
            "WHERE pi.deleted = false "
    )
    List<ProductImportDTO> findAllByDeletedIsFalse();

    @Query("SELECT NEW cg.dto.productImport.ProductImportDTO (" +
            "pi.id, " +
            "pi.size, " +
            "pi.color, " +
            "pi.code, " +
            "pi.date_added, " +
            "pi.price, " +
            "pi.quantity, " +
            "pi.productStatus, " +
            "pi.product" +
            ") " +
            "FROM ProductImport AS pi " +
            "WHERE (:#{#request.keyword} is null " +
            "OR pi.product.title LIKE :#{#request.keyword} " +
            "OR :#{#request.keyword} LIKE pi.code) " +
            "AND (:#{#request.fromDate} is null or pi.date_added BETWEEN :#{#request.fromDate} AND :#{#request.toDate}) " +
            "AND pi.deleted = false"
    )
    Page<ProductImportDTO> pageableByKeywordAndDate( ProductImportRequest request, Pageable pageable);


        @Query("SELECT NEW cg.dto.productImport.ProductImportResDTO(" +
            "pi.product.id, " +
            "pi.size, " +
            "pi.color, " +
            "pi.productStatus, " +
            "SUM(pi.quantity)) " +
            "FROM ProductImport AS pi where pi.product.id = :productId " +
            "GROUP BY pi.product.id, pi.size, pi.color, pi.productStatus")
            List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(@Param("productId") Long productId);


}
