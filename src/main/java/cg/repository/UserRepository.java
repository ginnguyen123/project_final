package cg.repository;

import cg.model.customer.Customer;
import cg.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    User findUserByCustomer(Customer customer);
}
