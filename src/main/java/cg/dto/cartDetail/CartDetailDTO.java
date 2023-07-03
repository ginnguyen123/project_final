package cg.dto.cartDetail;

import cg.dto.cart.CartDTO;

import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
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
@NoArgsConstructor
public class CartDetailDTO {

    private BigDecimal totalAmount;
    private Long quantity;
    private ESize size;
    private EColor color;
    private Long cartId;
    private Long productId;
    private String productTitle;
    private BigDecimal productPrice;

    public CartDetailDTO(BigDecimal totalAmount, Long quantity, ESize size, EColor color, Long cartId, Long productId, String productTitle, BigDecimal productPrice) {
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.cartId = cartId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }
    //pi.totalAmount,pi.quantity,pi.size,pi.color,pi.cart.id,pi.product.id,pi.product.price,pi.produc


    public CartDetail toCartDetail(){
        return new CartDetail()
                .setProduct(new Product())
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .setSize(size)
                .setColor(color)
               ;
    }



}
