package cg.repository;

import cg.model.customer.Customer;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByPhoneAndEmailAndAndFullName(String phone , String email , String fullName);
}
