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

//    @Query(value = "SELECT p.product_id, p.size, p.color, SUM(p.quantity) " +
//            "FROM product_import p " +
//            "GROUP BY p.product_id, p.size, p.color", nativeQuery = true)
//    @Query(value = "SELECT NEW cg.dto.productImport.ProductImportDTO(pi.) " +
//            "FROM product_import pi",nativeQuery = true)

// Tao ra 1 ProductImport DTO moi voi size, color ,... ENum => hung gia tri tu productimport
        @Query("SELECT NEW cg.dto.productImport.ProductImportResDTO(" +
            "pi.product.id, " +
            "pi.size, " +
            "pi.color, " +
            "pi.productStatus, " +
            "SUM(pi.quantity)) " +
            "FROM ProductImport AS pi where pi.product.id = :productId " +
            "GROUP BY pi.product.id, pi.size, pi.color, pi.productStatus")
            List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(@Param("productId") Long productId);

    @Query("SELECT SUM(pi.quantity) " +
            "FROM ProductImport AS pi " +
            "where pi.product.id = :productId " +
            "AND pi.color = :color " +
            "AND pi.size = :size " +
            "AND pi.deleted = FALSE " +
            "GROUP BY pi.product.id, pi.size, pi.color")
    Long checkQuantityProductImportBySizeAndColor(@Param("productId") Long productId,
                                                                      @Param("color")EColor color,
                                                                      @Param("size")ESize size);

    @Query("SELECT SUM(pi.quantity) " +
            "FROM ProductImport AS pi " +
            "where pi.product.id = :productId " +
            "AND pi.deleted = FALSE " +
            "AND pi.quantity > 0 " +
            "GROUP BY pi.product.id")
    Long checkQuantityProductImport(@Param("productId") Long productId);
}
