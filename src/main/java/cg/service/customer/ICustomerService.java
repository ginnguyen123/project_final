package cg.service.customer;

import cg.model.customer.Customer;
import cg.service.IGeneralService;

import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer,Long > {

    Optional<Customer> findCustomerByPhoneAndEmailAndAndFullName(String phone , String email , String fullName);
}
