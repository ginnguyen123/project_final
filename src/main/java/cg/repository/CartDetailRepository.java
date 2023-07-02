package cg.repository;

import cg.model.brand.Brand;
import cg.model.cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

}
