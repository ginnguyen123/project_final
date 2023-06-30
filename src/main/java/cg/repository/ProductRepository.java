package cg.repository;


import cg.dto.product.ProductListResponse;
import cg.dto.product.client.ProductResClientDTO;
import cg.model.enums.EColor;
import cg.model.enums.ESize;

import cg.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByDeletedIsFalse();

    @Query(value = "SELECT p.id, p.created_at, p.created_by, p.deleted, p.update_at, p.update_by," +
            " p.code, p.description, p.prices, p.title, p.brand_id, p.category_id,p.product_avatar_id, p.product_avatar_id" +
            " ,p.discount_id FROM products p INNER JOIN product_import pi " +
            "ON p.id=pi.product_id WHERE p.category_id= :idCategory AND pi.quantity>0 group by p.id  LIMIT 10",nativeQuery = true)
    List<Product> findProductsByCategoryWithLimit( @Param("idCategory") Long idCategory);

    @Query(value = "SELECT NEW cg.dto.product.ProductListResponse(" +
            "p.id,p.code,p.productAvatar,p.title,p.price,p.category.name" +
            ") " +
            "FROM Product p " +
            "WHERE  p.title like :search " +
            "OR p.code LIKE :search " +
            "OR p.price = :search" )
    Page<ProductListResponse> findAllWithSearch(@Param("search") String search, Pageable pageable);



    @Query(value = "SELECT NEW cg.dto.product.client.ProductResClientDTO(" +
            "prod.id, prod.title, prod.code, prod.price, prod.discount, prod.productAvatar, prod.brand, prod.category) " +
            "FROM Product AS prod " +
            "JOIN ProductImport AS prodImp ON prodImp.product = prod " +
            "JOIN Category AS cate ON cate = prod.category " +
            "JOIN Brand AS bra ON bra = prod.brand " +
            "WHERE prodImp.quantity > 0 " +
            "AND prod.title LIKE :search " +
            "OR prod.code LIKE :search " +
            "OR prodImp.code LIKE :search " +
            "OR prodImp.color LIKE :color " +
            "OR prodImp.size LIKE :size " +
            "OR bra.name LIKE :search " +
            "OR cate.name LIKE :search " +
            "OR cate.categoryParent.name LIKE :search")
    Page<ProductResClientDTO> findAllBySearchFromClient(@Param("search")String search, @Param("color") EColor color,
            @Param("size")ESize size, Pageable pageable);

    @Query(value = "SELECT NEW cg.dto.product.client.ProductResClientDTO(" +
            "prod.id, prod.title, prod.code, prod.price, prod.discount, prod.productAvatar, prod.brand, prod.category) " +
            "FROM Product AS prod " +
            "LEFT JOIN Category AS cate " +
            "JOIN ProductImport AS imp " +
            "WHERE imp.quantity > 0 " +
            "AND prod.deleted = false " +
            "AND cate.id = :id ")
    Page<ProductResClientDTO> findAllByCategory(@Param("id")Long id, Pageable pageable);



    @Query(value = "SELECT prod FROM Product AS prod " +
            "LEFT JOIN Discount AS disc ON disc = prod.discount " +
            "INNER JOIN ProductImport AS proImp ON proImp.product = prod " +
            "WHERE proImp.quantity > 0 " +
            "AND prod.deleted = FALSE " +
            "AND :day BETWEEN disc.startDate AND disc.endDate " +
            "OR prod.discount IS NULL " +
            "GROUP BY prod.id", nativeQuery = true)

    List<Product> findAllByDiscountTime(@Param("day") LocalDate date);



}
