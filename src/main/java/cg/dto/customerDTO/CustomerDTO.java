package cg.dto.customerDTO;

import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.model.customer.Customer;
import cg.model.enums.ESex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String fullname;
    private String email;
    private Date dateOfBirth;
    private ESex sex;
    private String phone;
    private List<LocationRegionDTO> locationRegionDTOS;

    //    public CustomerDTO(Long id, String fullname, String email,Date dateOfBirth, ESex sex, String phone) {
//
//    }
    public Customer toCustomer() {
        return new Customer()
                .setId(id)
                .setFullName(fullname)
                .setEmail(email)
                .setDateOfBirth(dateOfBirth)
                .setSex(sex)
                .setPhone(phone);
    }
}
