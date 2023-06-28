package cg.dto.cart;


import cg.dto.bill.BillDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.customerDTO.CustomerDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.dto.product.ProductDTO;
import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartCreResDTO {
    private CustomerDTO customerName;
    private List<CartDetailDTO> productTitle;
    private BigDecimal totalAmount;
    private LocationRegionDTO locationRegion;
    private ECartStatus status;

    public CartCreResDTO(Cart cart) {
        this.customerName = cart.getCustomer().toCustomerDTO();
        this.productTitle = cart.toCartCreResDTO().getProductTitle();
        this.totalAmount = cart.getTotalAmount();
        this.locationRegion = cart.getLocationRegion().toLocationRegionDTO();
        this.status = cart.getStatus();
    }
}
