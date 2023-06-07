package cg.model.bill;

import cg.model.BaseEntity;
import cg.model.customer.Customer;
import cg.model.location_region.LocationRegion;
import cg.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "[0-9]*")
    private String phone;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "location_region", referencedColumnName ="id", nullable = false)
    private LocationRegion locationRegion;

    @Column(name = "total_amounts",precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
