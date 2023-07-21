package cg.service.customer;

import cg.model.customer.Customer;
import cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer,Long > {

    Optional<Customer> findCustomerByEmail(String email);

    Boolean isEmail(String email);

    List<Customer> findCustomerByDeletedIsFalse();

    Optional<Customer> findCustomerByDeletedIsFalseAndUser_Username(String username);
}
