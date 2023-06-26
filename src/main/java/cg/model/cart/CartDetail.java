package cg.model.cart;

import cg.dto.cartDetail.CartDetailDTO;
import cg.model.BaseEntity;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_details")
public class CartDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantities", nullable = false)
    private Long quantity;


    @Column(name = "total_amounts", precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;


    @Column(name = "size", nullable = false)
    private ESize size;

    @Column(name = "color", nullable = false)
    private EColor color;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    //id,quantity,tAmount,size,color,cartID,productID, name,price


    public CartDetailDTO toCartDetailDTO() {
        return new CartDetailDTO()
                .setProduct(product)
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .setSize(size)
                .setColor(color)
                .setCart(cart.toCartDTO());
    }




}
