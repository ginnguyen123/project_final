package cg.repository;


import cg.dto.product.ProductListResponse;

import cg.dto.productImport.ProductImpListResDTO;
import cg.model.enums.EProductStatus;
import cg.model.product.Product;
import cg.utils.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByDeletedIsFalse();

    @Query(value = "SELECT p.id, p.created_at, p.created_by, p.deleted, p.update_at, p.update_by," +
            " p.code, p.description, p.prices, p.title, p.brand_id, p.category_id,p.product_avatar_id, p.product_avatar_id" +
            " ,p.discount_id FROM products p INNER JOIN product_import pi " +
            "ON p.id=pi.product_id WHERE p.category_id= :idCategory AND pi.quantity>0 group by p.id  LIMIT 10", nativeQuery = true)
    List<Product> findProductsByCategoryWithLimit( @Param("idCategory") Long idCategory);

    @Query(value = "SELECT prod.id , prod.title " +
            "FROM Product AS prod " +
            "INNER JOIN ProductImport AS prodImp ON prodImp.product.id = prod.id " +
            "WHERE prod.deleted = FALSE " +
            "AND ((prod.title LIKE :#{#search.keyword} " +
            "OR prod.code LIKE :#{#search.keyword} )" +
            "OR (prodImp.date_added BETWEEN :#{#search.fromDate} AND :#{#search.toDate} )) " +
            "GROUP BY prod.id")
    Page<Product> findProductsWithLimit(ProductRequest search, Pageable pageable);
    // search.fromDate = ""- > NULL
    // search.fromDate = "12-07-23" -> DATE

//    @Query(value = "SELECT prod.id , prod.title " +
//            "FROM Product AS prod " +
//            "INNER JOIN ProductImport AS prodImp ON prodImp.product.id = prod.id " +
//            "WHERE prod.deleted = FALSE " +
//            "OR (prod.title LIKE :#{#search.keyword} " +
//            "OR prod.code LIKE :#{#search.keyword} )" +
//            "OR (prodImp.date_added BETWEEN :#{#search.fromDate} AND :#{#search.toDate} " +
//            "OR :#{#search.fromDate} IS NULL) " +
//            "GROUP BY prod.id")
//    Page<Product> findProductsWithLimitNoStatus(ProductRequest search,Pageable pageable);

    @Query(value = "SELECT NEW cg.dto.product.ProductListResponse(" +
            "p.id,p.code,p.productAvatar,p.title,p.price,p.category.name" +
            ") " +
            "FROM Product p " +
            "WHERE (p.title like :search " +
            "OR p.code LIKE :search " +
            "OR p.price = :search) " +
            "AND p.deleted = FALSE " )
    Page<ProductListResponse> findAllWithSearch(@Param("search") String search, Pageable pageable);

//    query search ở trang home
    @Query(value = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, prod.discount_id, " +
            "prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id,prod.product_avatar_id " +
            "FROM products AS prod " +
            "INNER JOIN product_import AS prodImp ON prodImp.product_id = prod.id " +
            "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
            "LEFT JOIN brands AS bra ON bra.id = prod.brand_id " +
            "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
            "WHERE prodImp.quantity > 0 " +
            "AND prod.deleted = 0 " +
            "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
            "AND (prod.title LIKE :search " +
            "OR prod.code = :code " +
            "OR prodImp.color LIKE :search " +
            "OR bra.name LIKE :search " +
            "OR cate.name LIKE :search) " +
            "GROUP BY prod.id"
            ,
            countQuery = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, prod.discount_id, " +
                    "prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id,prod.product_avatar_id " +
                    "FROM products AS prod " +
                    "INNER JOIN product_import AS prodImp ON prodImp.product_id = prod.id " +
                    "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
                    "LEFT JOIN brands AS bra ON bra.id = prod.brand_id " +
                    "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
                    "WHERE prodImp.quantity > 0 " +
                    "AND prod.deleted = 0 " +
                    "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
                    "AND (prod.title LIKE :search " +
                    "OR prod.code = :code " +
                    "OR prodImp.color LIKE :search " +
                    "OR bra.name LIKE :search " +
                    "OR cate.name LIKE :search) " +
                    "GROUP BY prod.id"
            ,nativeQuery = true)
    Page<Product> findAllBySearchFromClient(@Param("search") String search,
                                            @Param("today") LocalDate today,
                                            @Param("code") String code,
                                            Pageable pageable);

//    query theo id category ở trang filter
    @Query(value = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, prod.discount_id, " +
            "prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id,prod.product_avatar_id " +
            "FROM products AS prod " +
            "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
            "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
            "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
            "WHERE prod.deleted = 0 " +
            "AND imp.quantity > 0 " +
            "AND prod.category_id = :category " +
            "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
            "GROUP BY prod.id",
            countQuery = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, prod.discount_id, " +
                    "prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id,prod.product_avatar_id  " +
                    "FROM products AS prod " +
                    "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
                    "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
                    "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
                    "WHERE prod.deleted = 0 " +
                    "AND imp.quantity > 0 " +
                    "AND prod.category_id = :category " +
                    "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
                    "GROUP BY prod.id" ,nativeQuery = true )
    Page<Product> findAllByCategoryToday (@Param("category") Long idCategory,
                                          @Param("today") LocalDate today,Pageable pageable);

//    query filter cho product theo category
//    @Query(value = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, prod.discount_id, " +
//            "prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id,prod.product_avatar_id " +
//            "FROM products AS prod " +
//            "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
//            "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
//            "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
//            "WHERE prod.deleted = 0 " +
//            "AND imp.quantity > 0 " +
//            "AND prod.category_id = :category " +
//            "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
//            "AND imp.color IN :colors " +
//            "AND imp.size IN :sizes " +
//            ":minmax " +
//            "GROUP BY prod.id ", nativeQuery = true)
//    Page<Product> findAllByCategoryFilter(@Param("category") Long idCategory,
//                                          @Param("today") LocalDate today,
//                                          @Param("minmax") String minMax,
//                                          @Param("colors")List<String> colors,
//                                          @Param("sizes")List<String> sizes,Pageable pageable);

    //query product theo discount còn hạn cho trang home
    @Query(value = "SELECT prod.id FROM products AS prod " +
            "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
            "LEFT JOIN product_import AS proImp ON proImp.product_id = prod.id " +
            "WHERE proImp.quantity > 0 " +
            "AND prod.deleted = 0 " +
            "AND :day BETWEEN disc.start_date AND disc.end_date " +
            "OR prod.discount_id IS NULL " +
            "GROUP BY prod.id ", nativeQuery = true)
    List<Long> findAllByDiscountTime(@Param("day") LocalDate date);

    List<Product> findByDeletedAndIdIn(boolean deleted, Collection<Long> id);


    @Query(value = "SELECT NEW cg.dto.productImport.ProductImpListResDTO(prod.id,prodImp.id, prodImp.size,prodImp.color, prodImp.price, " +
            "prodImp.quantity,prodImp.quantityExist, prodImp.selled, prodImp.productStatus, prod.title, prodImp.date_added) " +
            "FROM Product AS prod " +
            "INNER JOIN ProductImport AS prodImp ON prodImp.product.id = prod.id " +
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
            "AND prod.id IN :#{#search.idProduct}" )
//            "GROUP BY prodImp.id ")
    Page<ProductImpListResDTO> findAllForDataGrid(ProductRequest search , Pageable pageable);

}
