package cg.service.user;


import cg.dto.userDTO.UserClientResRegisterDTO;
import cg.model.customer.Customer;
import cg.model.user.Role;
import cg.model.user.User;
import cg.model.user.UserPrinciple;
import cg.repository.CustomerRepository;
import cg.repository.UserRepository;
import cg.service.customer.ICustomerService;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }
    @Override
    public User register(UserClientResRegisterDTO userResRegister) {
        userResRegister.setId(null);
        User user = save(userResRegister.toUser());
        String str = userResRegister.getCustomer().getDateOfBirth().toString();
        Date date = appUtils.stringToDate(str);
        Customer customer = userResRegister.getCustomer().toCustomer();
        customer.setId(null);
        customer.setUser(user);
        customer.setDateOfBirth(date);
        System.out.println(customer.toString());
        customerService.save(customer);
        return user;
    }
}
