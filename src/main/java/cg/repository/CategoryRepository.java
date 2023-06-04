package cg.repository;

import cg.model.category.Category;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    @Override
    boolean existsById(Long id);
}
