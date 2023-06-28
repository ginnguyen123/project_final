package cg.service.cart;

import cg.dto.cart.*;
import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.model.cart.Cart;
import cg.service.IGeneralService;
import cg.service.cart.response.CartListResponse;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart,Long> {
    Page<CartDTO> findAllByFilters(CartRequest keyword, Pageable pageable);


    Page<CartListResponse> pageableByKeyword(CartRequest request, Pageable pageable);

    void create(CartCreReqDTO cartCreReqDTO);

    Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id);

    CartListResponse update(CartUpReqDTO cartUpReqDTO);
}
