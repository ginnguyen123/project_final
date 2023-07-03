package cg.dto.cartDetail;

import cg.model.cart.CartDetail;
import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartDetailNotCart implements Validator {
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

    public CartDetailNotCart(CartDetail cartDetail) {
        this.id = cartDetail.getId();
        this.quantity = cartDetail.getQuantity();
        this.totalAmount = cartDetail.getTotalAmount();
        this.size = cartDetail.getSize();
        this.color = cartDetail.getColor();
        this.productTitle = cartDetail.getProduct().getTitle();
        this.productPrice = cartDetail.getProduct().getPrice();
        this.idProduct = cartDetail.getProduct().getId();
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
