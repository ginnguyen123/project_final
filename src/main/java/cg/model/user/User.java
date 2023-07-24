package cg.model.user;

import cg.dto.userDTO.UserDTO;
import cg.model.BaseEntity;
import cg.model.customer.Customer;
import cg.model.enums.EAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(nullable = true)
    private String password;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EAuthProvider provider;

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
