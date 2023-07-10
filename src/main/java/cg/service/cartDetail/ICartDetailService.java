package cg.service.cartDetail;

import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailNotCart;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.product.Product;
import cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICartDetailService extends IGeneralService<CartDetail,Long> {

    List<CartDetail> findCartDetailsByCartAndDeletedIsFalse(Cart cart);
    List<CartDetail> findAllByCart_IdAndAndDeletedIsFalse(Long id);

    Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id);

    CartDetailNotCart update(CartDetailUpReqDTO cartDetailUpReqDTO);

    BigDecimal getTotalAmountCartDetail(Product product, Long new_quantity);
}
