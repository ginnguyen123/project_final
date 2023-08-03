package cg.repository;

import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findAllByCart_IdAndDeletedIsFalse(Long id);


    List<CartDetail> findCartDetailsByCartAndDeletedIsFalse(Cart cart);

    @Query("SELECT NEW cg.dto.cartDetail.CartDetailDTO (pi.id,pi.totalAmount,pi.quantity,pi.size,pi.color, pi.cart.id,pi.product.id,pi.product.title,pi.product.price) " +
            "FROM CartDetail AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id);


    @Query("SELECT p.price  FROM Product AS p " +
            " where p.id = :id")
    BigDecimal getPriceWithProduct(Long id);

    @Query("SELECT SUM(detail.totalAmount) FROM CartDetail detail WHERE detail.cart = :cart ")
    BigDecimal totalAmoutByCart(@Param("cart")Cart cart);


}
