package cg.dto.cart;

import cg.anotation.ExistInDb;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartCreMiniCartReqDTO {
    private Long cardId;
    private String username;
    private Long productId;
    private BigDecimal price;
    private String size;
    private Long quantity;
    private String color;
    private String status;
}
