package cg.repository;

import cg.model.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> getAllByDeletedIsFalse();

    List<Discount> getAllByDeletedIsTrue();
}
