package cg.model.customer;

import cg.model.BaseEntity;
import cg.model.enums.ESex;
import cg.model.location_region.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


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

    @Column(nullable = false)
    private String phone;

    @OneToMany
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private List<LocationRegion> locationRegions;
}