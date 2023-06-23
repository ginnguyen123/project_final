package cg.dto.cart;


import cg.dto.bill.BillDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.customerDTO.CustomerDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.dto.product.ProductDTO;
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
public class CartCreResDTO {
    private CustomerDTO customerName;
    private List<CartDetailDTO> productTitle;
    private BigDecimal totalAmount;
    private LocationRegionDTO locationRegion;
    private ECartStatus status;

}
