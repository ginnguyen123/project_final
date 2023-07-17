package cg.service.user;

import cg.dto.userDTO.UserClientResRegisterDTO;
import cg.model.customer.Customer;
import cg.model.user.User;
import cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User,Long>, UserDetailsService {
    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String email);

    User findUserByCustomer(Customer customer);

    User register(UserClientResRegisterDTO userResRegister);
}
