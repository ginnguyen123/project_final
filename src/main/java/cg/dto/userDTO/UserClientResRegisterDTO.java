package cg.dto.userDTO;

import cg.dto.customerDTO.CustomerResRegisterDTO;
import cg.dto.roleDTO.RoleDTO;
import cg.model.user.User;
import cg.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientResRegisterDTO {
    private Long id;
    @NotBlank(message = "The username" + AppConstant.NOT_BLANK_FIELD_ERROR)
    private String username;
    @NotBlank(message = "The password" + AppConstant.NOT_BLANK_FIELD_ERROR)
        private String password;
    private CustomerResRegisterDTO customer;
    private RoleDTO role;

    public User toUser(){
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRole());
    }

}
