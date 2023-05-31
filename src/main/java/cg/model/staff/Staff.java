package cg.model.staff;

import cg.model.BaseEntity;
import cg.model.location_region.LocationRegion;
import cg.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    private String phone;
    private String email;
    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
