package cg.repository;

import cg.dto.product.ProductCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.category.Category;
import cg.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByDeletedFalse();

    //Khi nao thi dung JPQL va native sql
//    @Query(value = "SELECT NEW cg.dto.product.ProductCreResDTO(p.id," +
//            "p.title," +
//            "p.price," +
//            "p.description)" +
////            "p.productAvatar.fileUrl) " +
//            " FROM Product p " +
//            "INNER JOIN ProductImport pi " +
//            "ON p.id = pi.product.id " +
//            "WHERE p.category.id= :idCategory " +
//            "AND p.deleted=false AND pi.quantity>0 " +
//            "GROUP BY p.id LIMIT 2",nativeQuery = true)
//    List<ProductCreResDTO> findProductsByCategoryWithLimit( @Param("idCategory") Long idCategory);

    @Query(value = "SELECT * FROM products p INNER JOIN product_import pi ON p.id=pi.product_id WHERE p.category_id= :idCategory AND pi.quantity>0 GROUP BY p.id LIMIT 2",nativeQuery = true)
    List<Product> findProductsByCategoryWithLimit( @Param("idCategory") Long idCategory);

//    @Query(value = "SELECT NEW cg.dto.product.ProductCreResDTO(p.id,p.title,p.price,p.description,p.productAvatar.fileUrl) FROM Product p INNER JOIN ProductImport pi ON p.id=pi.product.id WHERE p.category.id= :idCategory AND p.deleted=false AND pi.quantity >0 GROUP BY p.id", nativeQuery = true)
//    List<ProductCreResDTO> findProductsByCategoryWithLimit(@Param("idCategory") Long idCategory);
}
