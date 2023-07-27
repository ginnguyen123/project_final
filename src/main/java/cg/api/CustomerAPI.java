package cg.api;


import cg.dto.customerDTO.CustomerDTO;
import cg.dto.customerDTO.CustomerInfoDTO;
import cg.model.customer.Customer;
import cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private ResponseEntity<?> getCustomerByUsername(@RequestParam(defaultValue = "") String username){

        Optional<Customer> customerOptional = customerService.findCustomerByDeletedIsFalseAndUser_Username(username);

        if(!customerOptional.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Customer customer = customerOptional.get();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = null;
        if (principal instanceof UserDetails) {
            currentUsername = ((UserDetails)principal).getUsername();
        } else {
            currentUsername = principal.toString();
        }
        CustomerInfoDTO customerInfo = customer.toCustomerInfo();
        customerInfo.setCurrentUsername(currentUsername);
        return new ResponseEntity<>(customerInfo,HttpStatus.OK);

    }
}
