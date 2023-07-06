package cg.repository;

import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByCategoryParentNotNull();

    List<Category> findCategoriesByCategoryParent_Id(Long id);

    List<Category> findCategoriesByCategoryParentAndDeletedIsFalse(Category categoryParent);

    List<Category> findAllByCategoryParentIsNull();

    List<Category> findAllByDeletedIsFalse();

    //Cach viet JPQL
    @Query("SELECT c FROM Category c where c.status = :status AND c.categoryParent is not null")
    List<Category> findAllCategoryByStatus(@Param("status") ECategoryStatus status);

    @Override
    boolean existsById(Long id);

    List<Category> findAllByCategoryParent_Id(Long id);

    Optional<Category> findByName(String name);
}
