package cg.repository;

import cg.dto.productImport.ProductImpListResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportResDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.ProductImport;
import cg.utils.ProductImportRequest;
import cg.utils.ProductRequest;
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
    Page<ProductImportDTO> pageableByKeywordAndDate(ProductRequest request, Pageable pageable);

    @Query("SELECT NEW cg.dto.productImport.ProductImportResDTO(" +
        "pi.product.id, " +
        "pi.size, " +
        "pi.color, " +
        "pi.productStatus, " +
        "SUM(pi.quantity)) " +
        "FROM ProductImport AS pi where pi.product.id = :productId " +
        "GROUP BY pi.product.id, pi.size, pi.color, pi.productStatus")
        List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(@Param("productId") Long productId);

//    @Query(value = "SELECT p.product_id, p.size, p.color, SUM(p.quantity) " +
//            "FROM product_import p " +
//            "GROUP BY p.product_id, p.size, p.color", nativeQuery = true)
//    @Query(value = "SELECT NEW cg.dto.productImport.ProductImportDTO(pi.) " +
//            "FROM product_import pi",nativeQuery = true)

// Tao ra 1 ProductImport DTO moi voi size, color ,... ENum => hung gia tri tu productimport
//        @Query("SELECT NEW cg.dto.productImport.ProductImportResDTO(" +
//            "pi.product.id, " +
//            "pi.size, " +
//            "pi.color, " +
//            "pi.productStatus, " +
//            "SUM(pi.quantity)) " +
//            "FROM ProductImport AS pi where pi.product.id = :productId " +
//            "GROUP BY pi.product.id, pi.size, pi.color, pi.productStatus")
//            List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(@Param("productId") Long productId);

    @Query(value = "SELECT SUM(pi.quantity) " +
            "FROM product_import AS pi " +
            "where pi.product_id = :productId " +
            "AND pi.color = :color " +
            "AND pi.size = :size " +
            "AND pi.deleted = FALSE " +
            "GROUP BY pi.product_id, pi.size, pi.color",nativeQuery = true)
    Long checkQuantityProductImportBySizeAndColor(@Param("productId") Long productId,
                                                                       @Param("color")String color,
                                                                      @Param("size")String size);

    @Query(value = "SELECT pi.color  FROM product_import AS pi  WHERE pi.product_id = :productId AND pi.quantity>0 AND pi.deleted = 0 GROUP BY pi.color", nativeQuery = true)
    List<EColor> getAllColorByProductAndQuantity(@Param("productId") Long productId);

    @Query("SELECT SUM(pi.quantity) " +
            "FROM ProductImport AS pi " +
            "where pi.product.id = :productId " +
            "AND pi.deleted = FALSE " +
            "AND pi.quantity > 0 " +
            "GROUP BY pi.product.id")
    Long checkQuantityProductImport(@Param("productId") Long productId);

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

    @Query(value = "SELECT NEW cg.dto.productImport.ProductImpListResDTO( prod.id,prodImp.id, prodImp.size, " +
            "prodImp.color, prodImp.price, prodImp.quantity,prodImp.quantityExist, prodImp.selled, prodImp.productStatus, prod.title, prodImp.date_added) " +
            "FROM ProductImport AS prodImp " +
            "INNER JOIN Product AS prod ON prod = prodImp.product " +
            "WHERE prod.deleted = FALSE AND prodImp.deleted = FALSE " +
            "AND (prod.title LIKE :#{#search.keyword} " +
//            "OR prod.category.name LIKE :#{#search.keyword} " +
            "OR prod.code LIKE :#{#search.keyword} )" +
//            "OR prod.brand.name LIKE :#{#search.keyword} " +
//            "OR prod.price = :#{#search.keyword} " +
//            "OR prodImp.price = :#{#search.keyword} " +
//            "OR prodImp.code LIKE :#{#search.keyword}) " +
//            "OR prodImp.quantity = :#{#search.keyword} " +
//            "OR prodImp.quantityExist = :#{#search.keyword} " +
            "AND (prodImp.date_added BETWEEN :#{#search.fromDate} AND :#{#search.toDate} " +
            "OR :#{#search.fromDate} IS NULL) " +
            "AND prodImp.product.id IN :#{#search.idProduct} " +
            "GROUP BY prodImp.id ")
    Page<ProductImpListResDTO> findAllForDataGrid(ProductRequest search , Pageable pageable);

    @Query(value = "SELECT NEW cg.dto.productImport.ProductImpListResDTO(prod.id,prodImp.id, prodImp.size, " +
            "prodImp.color, prodImp.price, prodImp.quantity,prodImp.quantityExist, prodImp.selled, prodImp.productStatus, prod.title, prodImp.date_added) " +
            "FROM ProductImport AS prodImp " +
            "INNER JOIN Product AS prod ON prod.id = prodImp.product.id " +
            "WHERE prod.deleted = FALSE AND prodImp.deleted = FALSE " +
            "AND prod.id IN :idProducts " +
            "AND (prod.title LIKE :#{#request.keyword} " +
            "OR (prodImp.date_added BETWEEN :#{#request.fromDate} AND :#{#request.toDate} " +
            "OR :#{#request.fromDate} IS NULL)) " +
            "AND prodImp.productStatus = :status ")
    List<ProductImpListResDTO> getAllByIdProduct(ProductImportRequest request,
                                                 @Param("idProducts") List<Long> idProducts,
                                                 @Param("status")EProductStatus status);

    @Query(value = "SELECT NEW cg.dto.productImport.ProductImpListResDTO(prod.id,prodImp.id, prodImp.size, " +
            "prodImp.color, prodImp.price, prodImp.quantity,prodImp.quantityExist, prodImp.selled, prodImp.productStatus, prod.title, prodImp.date_added) " +
            "FROM ProductImport AS prodImp " +
            "INNER JOIN Product AS prod ON prod.id = prodImp.product.id " +
            "WHERE prod.deleted = FALSE AND prodImp.deleted = FALSE " +
            "AND prod.id IN :idProducts " +
            "AND (prod.title LIKE :#{#request.keyword} " +
            "OR (prodImp.date_added BETWEEN :#{#request.fromDate} AND :#{#request.toDate} " +
            "OR :#{#request.fromDate} IS NULL))" +
            "AND :#{#request.status} = :status " )

    List<ProductImpListResDTO> getAllByIdProductNotSearchStatus(ProductImportRequest request,
                                                                @Param("status")String status,
                                                            @Param("idProducts") List<Long> idProducts);

}
