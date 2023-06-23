package cg.service.cart;

import cg.dto.cart.CartCreReqDTO;
import cg.dto.cart.CartCreResDTO;
import cg.dto.cart.CartDTO;
import cg.model.cart.Cart;
import cg.service.IGeneralService;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICartService extends IGeneralService<Cart,Long> {
    Page<CartDTO> findAllByFilters(CartRequest keyword, Pageable pageable);


    Page<CartDTO> pageableByKeyword(CartRequest request, Pageable pageable);

    CartCreResDTO create(CartCreReqDTO cartCreReqDTO);
}
