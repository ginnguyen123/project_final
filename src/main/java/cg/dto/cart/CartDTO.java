package cg.dto.cart;


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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long customerId;
    private CustomerDTO customer;
    private BigDecimal totalAmount;
    private Long locationRegionId;
    private LocationRegionDTO locationRegionDTO;
    private ECartStatus status;
    private List<CartDetailDTO> cartDetailDTOList;

    //CartDTO (pi.id,pi.customer.id,pi.totalAmount,pi.locationRegion.id,pi.status
    public CartDTO(Long id,Long customerId,BigDecimal totalAmount,Long locationRegionId,ECartStatus status){
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.locationRegionId = locationRegionId;
        this.status = status;
    }


    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setCustomer(customer.toCustomer())
                .setLocationRegion(locationRegionDTO.toLocationRegion())
                .setStatus(status)
                .setTotalAmount(totalAmount);
    }


}
