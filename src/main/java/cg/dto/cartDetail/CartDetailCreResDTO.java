package cg.dto.cartDetail;

import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailCreResDTO {
    private String title;
    private BigDecimal totalAmountDetail;
    private Long quantity;
    private ESize size;
    private EColor color;
    private Long cartId;
    private Product product = new Product(title);


}
