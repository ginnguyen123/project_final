package cg.repository;

import cg.dto.cart.CartCreResDTO;
import cg.dto.cart.CartDTO;
import cg.model.cart.Cart;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
            "AND cr.deleted = false"
    )

    Page<CartDTO> pageableByKeyword(CartRequest request, Pageable pageable);
}
