package cg.dto.cart;


import cg.model.bill.Bill;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
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
public class CartDTO {

    private Long id;
    private Bill customer;
    private Product product;
    private Bill information;
    private ECartStatus status;
}
