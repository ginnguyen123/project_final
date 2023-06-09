package cg.model.user;

import cg.dto.userDTO.UserDTO;
import cg.model.BaseEntity;
import cg.model.customer.Customer;
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
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    public UserDTO toUserDTO(){
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password);
    }
}
