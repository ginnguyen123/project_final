package cg.api;


import cg.dto.customerDTO.CustomerDTO;
import cg.model.customer.Customer;
import cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class CustomerAPI {

    private final ICustomerService customerService;

//    @GetMapping
//    private ResponseEntity<?> getAllCustomersDeletedIsFalse(){
//        List<Customer> customers = customerService.findCustomerByDeletedIsFalse();
//        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> customer.toCustomerDTO()).collect(Collectors.toList());
//        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
//    }

    @GetMapping
    private ResponseEntity<?> getCustomerByUsername(@RequestParam String username){
        Optional<Customer> customerOptional = customerService.findCustomerByDeletedIsFalseAndUser_Username(username);

        if(!customerOptional.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Customer customer = customerOptional.get();

        return new ResponseEntity<>(customer.toCustomerInfo(),HttpStatus.OK);

    }
}
