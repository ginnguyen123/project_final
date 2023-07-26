package cg.model.cart;

import cg.dto.cartDetail.CartDetailNotCart;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailResDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
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
    @Enumerated(EnumType.STRING)
    private ESize size;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private EColor color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    public CartDetailDTO toCartDetailDTO() {
        return new CartDetailDTO()
                .setProductId(product.getId())
                .setProductTitle(product.getTitle())
                .setProductPrice(product.getPrice())
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .setSize(size)
                .setColor(color)
                .setCartId(cart.getId())
        ;
    }

    public CartDetailUpReqDTO toCartDetailUpReqDTO() {
        return new CartDetailUpReqDTO()
                .setId(id)
                .setTotalAmountDetail(totalAmount)
                .setProductPrice(product.getPrice())
                .setProductTitle(product.getTitle())
                .setProductId(product.getId())
                .setQuantity(quantity)
                .setSize(size)
                .setColor(color);
    }

    public CartDetailNotCart toCartDetailNotCart(){
        return new CartDetailNotCart()
                .setId(id)
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .setIdProduct(product.getId())
                .setProductPrice(product.getPrice())
                .setProductTitle(product.getTitle())
                .setColor(color)
                .setSize(size)
                .setIdCart(cart.getId())
                ;
    }

    public CartDetailResDTO toCartDetailResDTO() {
        return new CartDetailResDTO()
                .setId(id)
                .setProductId(product.getId())
                .setColor(color)
                .setSize(size)
                .setQuantity(quantity)
                .setPrice(product.getPrice())
                .setTitle(product.getTitle())
                .setTotalAmountItem(totalAmount)
                .setAvt(product.getProductAvatar().getFileUrl());
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", size=" + size +
                ", color=" + color +
                ", cart=" + cart +
                ", product=" + product.getId() +
                '}';
    }
}
