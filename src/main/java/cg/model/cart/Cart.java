package cg.model.cart;

import cg.dto.cart.CartCreResDTO;
import cg.dto.cart.CartDTO;
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


    @Column(nullable = false)
    @Pattern(regexp = "[0-9]*")
    private String phone_receiver;

    @Column(nullable = false)
    private String name_receiver;

    @OneToOne
    @JoinColumn(name = "location_region", referencedColumnName = "id", nullable = true)
    private LocationRegion locationRegion;


    @Column(name = "total_amounts", precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
    private Customer customer;

    @Column(nullable = false)
    private ECartStatus status;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;


    public CartDTO toCartDTO() {
        return new CartDTO()
                .setId(id)
                .setCustomer(customer.toCustomerDTO())
                .setLocationRegionDTO(locationRegion.toLocationRegionDTO())
                .setTotalAmount(totalAmount)
                .setStatus(status)
                ;
    }

    public CartCreResDTO toCartCreResDTO(){
        return new CartCreResDTO()
                .setCustomerName(customer.toCustomerDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setStatus(status)
                .setTotalAmount(totalAmount);
    }




}
