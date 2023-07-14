package cg.dto.customerDTO;

import cg.model.customer.Customer;
import cg.model.enums.ESex;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResRegisterDTO {
    private Long id;
    private String fullName;
    @NotBlank(message = "The email" + AppConstant.NOT_BLANK_FIELD_ERROR)
    @Email
    private String email;
    private LocalDate dateOfBirth;
    private String sex;
    @NotBlank(message = "The phone" + AppConstant.NOT_BLANK_FIELD_ERROR)
    @Pattern(regexp = "^[0-9]*$")
    private String phone;

    public Customer toCustomer(){
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setSex(ESex.valueOf(sex));
    }

}
