package cg.repository;

import cg.model.category.Category;
import cg.model.enums.ECategory;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    @Query(value = "Select c from Category c where c.status=:status")
    List<Category> findAllByStatus(ECategory status);

    @Override
    boolean existsById(Long id);

    List<Category> findAllByCategoryParent_Id(Long id);
}
