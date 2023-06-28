package cg.dto.cart;

import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartCreReqDTO implements Validator {

//list cartdetail

    private String fullName;
    private String email;
    private String phone;
    private List<CartDetailCreReqDTO> cartDetailDTOList;
    private BigDecimal totalAmount;
    private LocationRegionDTO locationRegion;
    private String status;



    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCreReqDTO cartCreReqDTO = (CartCreReqDTO) target;

        String fullName = cartCreReqDTO.getFullName();
        String email = cartCreReqDTO.getEmail();
        String phone = cartCreReqDTO.getPhone();
        String totalAmount = String.valueOf(cartCreReqDTO.getTotalAmount());
        String locationRegion = String.valueOf(cartCreReqDTO.getLocationRegion());



        if (fullName.isEmpty()) {
            errors.reject("fullName.null", "Customer fullName must not be null");
        }

        if (locationRegion.isEmpty()) {
            errors.reject("Address.null", "Address must not be null");
        }

        if (totalAmount.isEmpty()) {
            errors.reject("date.null", "Product date must not be null");
        }
        if (email != null && email.length() > 0) {
            if (!email.matches("^(.+)@(\\S+)$")) {
                    errors.rejectValue("email", "Email must be in the correct format");
            }
        } else {
                errors.rejectValue("email", "email.null", "Email must not be null");
            }

        if (phone != null && phone.length() > 0) {
            if (!phone.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("phone", "phone.number", "phone must be a number");
            }
            if (phone.length() == 11) {
                errors.rejectValue("phone", "phone", "Phone number must be 10 digits");
            }

        }
    }
}








