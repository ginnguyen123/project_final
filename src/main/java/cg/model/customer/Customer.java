package cg.model.customer;

import cg.dto.customerDTO.CustomerDTO;
import cg.dto.customerDTO.CustomerInfoDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.model.BaseEntity;
import cg.model.cart.Cart;
import cg.model.enums.ESex;
import cg.model.location_region.LocationRegion;
import cg.model.user.User;
import cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    private ESex sex;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

//    @OneToMany(mappedBy = "customer")
//    private List<Cart> carts;
    @Column
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<LocationRegion> locationRegions;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setDateOfBirth(dateOfBirth)
                .setSex(sex)
                .setPhone(phone)
                .setLocationRegionDTOS(locationRegions.stream().map(LocationRegion::toLocationRegionDTO).collect(Collectors.toList()))
                ;
    }

    public CustomerInfoDTO toCustomerInfo(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDateOfBirth = simpleDateFormat.format(dateOfBirth);
        return new CustomerInfoDTO(id, fullName, email, phone, strDateOfBirth);
    }

    public CustomerDTO toCustomerDTO(List<LocationRegionDTO> locationRegionDTOS) {
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setDateOfBirth(dateOfBirth)
                .setSex(sex)
                .setPhone(phone)
                .setLocationRegionDTOS(locationRegionDTOS)
                ;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                ", user=" + user +
                ", phone='" + phone + '\'' +
                ", locationRegions=" + locationRegions +
                ", carts=" + carts +
                '}';
    }
}
