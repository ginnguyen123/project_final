package cg.repository;

import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {


    List<CartDetail> findAllByCart_IdAndDeletedIsFalse(Long id);


    @Query("SELECT NEW cg.dto.cartDetail.CartDetailDTO (pi.id,pi.totalAmount,pi.quantity,pi.size,pi.color, pi.cart.id,pi.product.id,pi.product.title,pi.product.price) " +
            "FROM CartDetail AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id);


}
