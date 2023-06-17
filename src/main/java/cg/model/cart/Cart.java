package cg.model.cart;

import cg.model.BaseEntity;
import cg.model.bill.Bill;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import cg.model.product.Product;
import cg.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "id", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "group_by_product",referencedColumnName = "id" , nullable = false)
    private Product product;

    @Column(nullable = false)
    private ECartStatus status;





}
