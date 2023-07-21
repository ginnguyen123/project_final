package cg.dto.cartDetail;

import cg.model.enums.EColor;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailListReqDTO {
    private Long id;
    private Long idProduct;
    private EColor color;
    private ESize size;
    private Long quantity;
    private BigDecimal totalAmountDetail;
}
