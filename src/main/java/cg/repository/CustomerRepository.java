package cg.repository;

import cg.model.customer.Customer;
import cg.model.product.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
