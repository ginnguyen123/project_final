package cg.service.customer;

import cg.model.customer.Customer;
import cg.repository.CustomerRepository;
import cg.service.ExistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService, ExistService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public boolean isValidService(Class<?> clazz) {
        return Customer.class.getName().equals(clazz.getName());
    }

    @Override
    public boolean exist(Object id) {
        return customerRepository.existsById((Long) id);
    }

    @Override
    public Boolean isEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
