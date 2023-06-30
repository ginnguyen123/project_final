package cg.dto.cartDetail;

import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartDetailNotCart {
    private Long id;
    private Long quantity;
    private BigDecimal totalAmount;
    private ESize size;
    private EColor color;
    private String productTitle;
    private BigDecimal productPrice;
    private Long idProduct;
    private Long idCart;
    private ECartStatus status;


}
