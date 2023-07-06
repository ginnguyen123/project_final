package cg.service.cartDetail;

import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailNotCart;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService extends IGeneralService<CartDetail,Long> {
//    Long getCartDetailWithProductAndSizeAndColor(ESize size , EColor color , Long idProduct);

    Long getCartDetailWithProduct(Long idProduct);
    List<CartDetail> findAllByCart_IdAndDeletedIsFalse(Long id);

    Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id);

    CartDetailNotCart update(CartDetailUpReqDTO cartDetailUpReqDTO);
}
