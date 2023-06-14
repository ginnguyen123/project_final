package cg.dto.roleDTO;


import cg.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String code;

    public Role toRole(){
        return new Role()
                .setId(id)
                .setCode(code);
    }
}
