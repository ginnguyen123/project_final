package cg.dto.cartDetail;

import cg.dto.cart.CartDTO;

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
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {

    private BigDecimal totalAmount;
    private Long quantity;
    private ESize size;
    private EColor color;
    private CartDTO cart;
    private Long id;
    private String title;
    private BigDecimal price;
    private Product product = new Product(id,title,price);


    public CartDetail toCartDetail(){
        return new CartDetail()
                .setProduct(product)
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .setSize(size)
                .setColor(color)
                .setCart(cart.toCart());
    }



}
