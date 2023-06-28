package cg.dto.cartDetail;

import cg.model.cart.CartDetail;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailCreReqDTO implements Validator {

    private Long productId;
    private Long quantity;
    private ESize size;
    private EColor color;

    public CartDetail toCartDetail() {
        return new CartDetail()
                .setId(null)
                .setQuantity(quantity)
                .setSize(size)
                .setColor(color)
                ;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
