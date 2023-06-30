package cg.service.cartDetail;

import cg.dto.cartDetail.CartDetailDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService extends IGeneralService<CartDetail,Long> {

    List<CartDetail> findAllByCart_IdAndAndDeletedIsFalse(Long id);

    Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id);
}
