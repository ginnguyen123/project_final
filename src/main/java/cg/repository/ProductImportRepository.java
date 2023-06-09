package cg.repository;

import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
                    "WHERE pi.deleted = false "+
                    "AND pi.id = :id "
            )
    Optional<ProductImportDTO> getProductImportDTOByIdDeletedIsFalse(Long id);


}
