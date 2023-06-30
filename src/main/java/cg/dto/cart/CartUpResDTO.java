package cg.dto.cart;

import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartUpResDTO {

    private Long id;
    private String name_receiver;
    private String phone_receiver;
    private List<CartDetailUpReqDTO> cartDetailDTOList;
    private BigDecimal totalAmount;
    private LocationRegionDTO locationRegion;
    private ECartStatus status;

    public CartUpResDTO(Cart cart) {
        this.id = cart.getId();
        this.name_receiver = cart.getName_receiver();
        this.phone_receiver = cart.getPhone_receiver();
        this.totalAmount = cart.getTotalAmount();
        this.cartDetailDTOList = cart.toCartUpReqDTO().getCartDetailDTOList();
        this.status = cart.getStatus();
        this.locationRegion = cart.getLocationRegion().toLocationRegionDTO();
    }
}
