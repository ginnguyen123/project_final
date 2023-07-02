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
public class CartDetailResDTO {
    private Long productId;
    private EColor color;
    private ESize size;
    private Long quantity;
    private BigDecimal price;
    private String title;
    private BigDecimal totalAmountItem;
    private BigDecimal totalAmountCart;
    private String avt;
}
