package cg.repository;

import cg.dto.cart.CartCreResDTO;
import cg.dto.cart.CartDTO;
import cg.dto.cart.CartUpReqDTO;
import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT cr FROM Cart AS cr " +
            " join CartDetail cd on cr.id = cd.cart.id " +
            " join Customer  cus on cus.id = cr.customer.id" +
            " join LocationRegion lr on cr.locationRegion.id = lr.id " +

            "WHERE (cus.fullName LIKE :#{#request.keyword} or " +
            "cd.product.title LIKE :#{#request.keyword} or " +
            "lr.districtName LIKE :#{#request.keyword} or " +
            "lr.wardName LIKE :#{#request.keyword} or " +
            "lr.provinceName LIKE :#{#request.keyword} or " +
            "lr.address LIKE :#{#request.keyword})" +
            "AND cr.deleted = false " +
            "GROUP BY cr.id"
    )
    Page<Cart> pageableByKeyword(CartRequest request, Pageable pageable);



    @Query("SELECT NEW cg.dto.cart.CartDTO (pi.id,pi.customer.id,pi.totalAmount,pi.locationRegion.id,pi.status) " +
            "FROM Cart AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id);

    @Query("SELECT NEW cg.dto.cart.CartUpReqDTO (pi.id,pi.name_receiver,pi.phone_receiver,pi.totalAmount,pi.locationRegion.id,pi.status) " +
            "FROM Cart AS pi " +
            "WHERE pi.deleted = false " +
            "AND pi.id = :id "
    )
    Optional<CartUpReqDTO> getCartDTOByCartDetail(Long id);

    @Query("SELECT c FROM Cart AS c WHERE c.customer.id = :customerId AND c.status = :status")
    Cart findCartsByCustomerIdAndStatusIsCart(@Param("customerId")Long customerId , @Param("status") ECartStatus status);
}

