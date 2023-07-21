package cg.repository;

import cg.model.customer.Customer;
import cg.model.location_region.LocationRegion;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);

    Boolean existsByEmail(String email);

    List<Customer> findCustomerByDeletedIsFalse();

    Optional<Customer> findCustomerByDeletedIsFalseAndUser_Username(String username);
}
