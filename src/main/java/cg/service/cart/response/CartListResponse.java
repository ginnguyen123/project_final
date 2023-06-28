package cg.service.cart.response;

import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartListResponse {
    private Long id;

    private String customerName;

    private String productsName;

    private BigDecimal total;

    private String address;

    private ECartStatus status;


    public CartListResponse(Cart cart){
        id = cart.getId();
        customerName = cart.getName_receiver();
        total = cart.getTotalAmount();
        productsName = cart.getCartDetails().stream().map(p -> p.getProduct().getTitle()).collect(Collectors.joining(", "));
        address = cart.getLocationRegion().getAddress() + ", " + cart.getLocationRegion().getWardName() + ", " + cart.getLocationRegion().getDistrictName() + ", " + cart.getLocationRegion().getProvinceName();
        status = cart.getStatus();
    }

}
