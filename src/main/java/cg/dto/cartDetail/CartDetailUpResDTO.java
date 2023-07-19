package cg.dto.cartDetail;

import cg.model.enums.EColor;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailUpResDTO {
    private Long id;
    private Long productId;
    private Long productImpQuantity;
    private ESize size;
    private EColor color;
    private Long quantity;
    private String productTitle;
    private BigDecimal productPrice;


}
