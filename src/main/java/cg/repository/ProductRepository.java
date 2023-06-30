package cg.repository;


import cg.dto.product.ProductListResponse;

import cg.dto.product.client.ProductFilterDTO;
import cg.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByDeletedIsFalse();

    @Query(value = "SELECT p.id, p.created_at, p.created_by, p.deleted, p.update_at, p.update_by," +
            " p.code, p.description, p.prices, p.title, p.brand_id, p.category_id,p.product_avatar_id, p.product_avatar_id" +
            " ,p.discount_id FROM products p INNER JOIN product_import pi " +
            "ON p.id=pi.product_id WHERE p.category_id= :idCategory AND pi.quantity>0 group by p.id  LIMIT 10",nativeQuery = true)
    List<Product> findProductsByCategoryWithLimit( @Param("idCategory") Long idCategory);

    @Query(value = "SELECT new cg.dto.product.ProductListResponse(" +
            "p.id,p.code,p.productAvatar,p.title,p.price,p.category.name" +
            ") " +
            "FROM Product p " +
            "WHERE  p.title like :search " +
            "OR p.code LIKE :search " +
            "OR p.price = :search" )
    Page<ProductListResponse> findAllWithSearch(@Param("search") String search, Pageable pageable);


    @Query(value = "SELECT prod FROM Product AS prod " +
            "INNER JOIN Discount AS disc ON disc = prod.discount " +
            "WHERE :day BETWEEN disc.startDate AND disc.endDate " +
            "AND prod.deleted = FALSE")
    List<Product> findAllByDiscountTime(@Param("day")Date date);


    @Query(value = "SELECT new cg.dto.product.client.ProductFilterDTO(" +

            "p.id," +
            "p.title," +
            "p.brand.name," +
            "pi.color," +
            "pi.size," +
            "p.category.name," +
            "p.price," +
            "p.discount.name" +
            ") " +
            "FROM Product AS p " +
            "JOIN ProductImport AS pi " +
            "WHERE p.title LIKE :search " +)

}
