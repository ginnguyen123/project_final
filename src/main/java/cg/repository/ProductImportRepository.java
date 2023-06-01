package cg.repository;

import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImportRepository extends JpaRepository<ProductImport, Long> {

}
