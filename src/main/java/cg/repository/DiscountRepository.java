package cg.repository;

import cg.model.discount.Discount;
import cg.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> getAllByDeletedIsFalse();

    List<Discount> getAllByDeletedIsTrue();

    Optional<Discount> findDiscountByIdAndDeletedIsFalse(Long id);

    Optional<Discount> findDiscountByProducts(Product product);

}
