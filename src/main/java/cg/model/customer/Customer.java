package cg.model.customer;

import cg.dto.customerDTO.CustomerDTO;
import cg.model.BaseEntity;
import cg.model.cart.Cart;
import cg.model.enums.ESex;
import cg.model.location_region.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_names", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;


    private ESex sex;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<LocationRegion> locationRegions;

    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
                .setId(id)
                .setFullname(fullName)
                .setEmail(email)
                .setDateOfBirth(dateOfBirth)
                .setSex(sex)
                .setPhone(phone)
                .setLocationRegionDTOS(locationRegions.stream().map(LocationRegion::toLocationRegionDTO).collect(Collectors.toList()))
                ;
    }
}
