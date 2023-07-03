package cg.dto.cartDetail;

import cg.model.cart.CartDetail;
import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
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
public class CartDetailUpReqDTO implements Validator {

    private Long id;
    private Long productId;
    private Long quantity;
    private ESize size;
    private EColor color;
    private ECartStatus status;
    private String productTitle;
    private BigDecimal productPrice;


    public CartDetail toCartDetail() {
        return new CartDetail()
                .setId(null)
                .setQuantity(quantity)
                .setSize(size)
                .setColor(color)
                ;
    }

//    public CartDetail toCartDetail(Product product) {
//        return new CartDetail()
//                .setId(null)
//                .setQuantity(quantity)
//                .setSize(size)
//                .setColor(color)
//                .setProduct(product.getProductId());
//                ;
//    }
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
