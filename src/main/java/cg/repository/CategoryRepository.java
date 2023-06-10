package cg.repository;

import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    //Cach viet native SQL
//    @Query(value = "SELECT * FROM category c WHERE c.status= :status",nativeQuery = true)
//    List<Category> findAllCategoryByStatus(@Param("status") ECategoryStatus status);

    //Cach viet JPQL
    @Query("SELECT c FROM Category c where c.status = :status")
    List<Category> findAllCategoryByStatus(@Param("status") ECategoryStatus status);


    @Override
    boolean existsById(Long id);

    List<Category> findAllByCategoryParent_Id(Long id);
}
