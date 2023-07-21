package cg.model.cart;

import cg.dto.cart.*;
import cg.dto.cart.CartUpResDTO;
import cg.model.BaseEntity;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import cg.model.location_region.LocationRegion;
import cg.model.user.User;
import cg.service.cart.response.CartListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Column(nullable = true)
    @Pattern(regexp = "[0-9]{10}")
    private String phone_receiver;

    @Column(nullable = true)
    private String name_receiver;

    @OneToOne
    @JoinColumn(name = "location_region", referencedColumnName = "id", nullable = true)
    private LocationRegion locationRegion;


    @Column(name = "total_amounts", precision = 10, scale = 0, nullable = true)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;


    @Column(nullable = true)
//    @Enumerated(EnumType.STRING)
    private ECartStatus status;

    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER)
    private List<CartDetail> cartDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public CartDTO toCartDTO() {
        return new CartDTO()
                .setId(id)
                .setCustomer(customer.toCustomerDTO())
                .setLocationRegionDTO(locationRegion.toLocationRegionDTO())
                .setTotalAmount(totalAmount)
                .setStatus(status)
//                .setCartDetailDTOList(
//                        cartDetails.stream()
//                                .map(CartDetail::toCartDetailDTO)
//                                .collect(Collectors.toList()))
                ;
    }

    public CartResDTO toCartResDTO() {
        return new CartResDTO()
                .setId(id)
                .setTotalAmountCart(totalAmount);
    }

    public CartCreResDTO toCartCreResDTO(){
        return new CartCreResDTO()
                .setId(id)
                .setCartDetailDTOList(cartDetails.stream().map(item -> item.toCartDetailUpReqDTO()).collect(Collectors.toList()))
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setStatus(status)
                .setTotalAmount(totalAmount)
                .setName_receiver(name_receiver)
                .setPhone_receiver(phone_receiver)
                ;
    }

    public CartUpReqDTO toCartUpReqDTO() {
        return new CartUpReqDTO()
                .setEmail(customer.getEmail())
                .setName_receiver(name_receiver)
                .setPhone_receiver(phone_receiver)
//                .setCartDetailDTOList(cartDetails.stream().map(CartDetail::toCartDetailUpReqDTO).collect(Collectors.toList()))
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setTotalAmount(totalAmount)
                .setStatus(String.valueOf(status))
                .setId(id);
    }

    public CartUpResDTO toCartUpResDTO() {
        return new CartUpResDTO()
                .setId(id)
                .setName_receiver(name_receiver)
                .setPhone_receiver(phone_receiver)
                .setTotalAmount(totalAmount)
                .setStatus(status)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setCartDetailDTOList(cartDetails.stream().map(item -> item.toCartDetailUpReqDTO()).collect(Collectors.toList()));

    }

    public CartCheckoutDTO toCartCheckoutDTO() {
        return new CartCheckoutDTO()
                .setCartId(id)
                .setPhone_receiver(phone_receiver)
                .setName_receiver(name_receiver)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setCartDetailResDTOList(cartDetails.stream().map(item->item.toCartDetailResDTO()).collect(Collectors.toList()))
                .setTotalAmount(totalAmount)
                .setCustomerId(customer.getId());
    }
}
