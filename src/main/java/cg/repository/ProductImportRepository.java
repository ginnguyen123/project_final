package cg.repository;

import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportResDTO;
import cg.model.enums.EColor;
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

import java.time.LocalDate;
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

    @Query(value = "SELECT imp.color FROM product_import AS imp " +
            "INNER JOIN products AS prod " +
            "LEFT JOIN discounts AS disc ON prod.discount_id = disc.id " +
            "LEFT JOIN category AS categ ON categ.id = prod.category_id " +
            "WHERE imp.deleted = 0 " +
            "AND imp.quantity > 0 " +
            "AND prod.category_id = :categoryId " +
            "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
            "GROUP BY imp.color", nativeQuery = true)
    List<EColor> findAllColorCategory(@Param("categoryId") Long id, @Param("today")LocalDate today);

    @Query(value = "SELECT imp.size FROM product_import AS imp " +
            "INNER JOIN products AS prod " +
            "LEFT JOIN discounts AS disc ON prod.discount_id = disc.id " +
            "LEFT JOIN category AS categ ON categ.id = prod.category_id " +
            "WHERE imp.deleted = 0 " +
            "AND imp.quantity > 0 " +
            "AND prod.category_id = :categoryId " +
            "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
            "GROUP BY imp.size", nativeQuery = true)
    List<ESize> findAllSizeCategory(@Param("categoryId") Long id, @Param("today")LocalDate today);
}
