package cg.dto.cart;

import cg.dto.cartDetail.CartDetailResDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutDTO implements Validator {
    private Long cartId;
    private String name_receiver;
    @Pattern(regexp = "[0-9]{10}")
    private String phone_receiver;
    private LocationRegionDTO locationRegion;
    private BigDecimal totalAmount;
    private List<CartDetailResDTO> cartDetailResDTOList;
    private Long customerId;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCheckoutDTO cartCheckoutDTO = (CartCheckoutDTO) target;
        String name_receiver = cartCheckoutDTO.getName_receiver();
        String phone_receiver = cartCheckoutDTO.getPhone_receiver();
        String address = String.valueOf(cartCheckoutDTO.getLocationRegion()) ;
        if (name_receiver.isEmpty()) {
            errors.reject("fullName.null", "FullName of receiver must not be null");
        }

        if (phone_receiver != null && phone_receiver.length() > 0) {
            if (!phone_receiver.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("phone", "phone.number", "phone must be a number");
            }
            if (phone_receiver.length() == 11) {
                errors.rejectValue("phone", "phone", "Phone number must be 10 digits");
            }

        }

        if (address.isEmpty()) {
            errors.reject("Address.null","Address must not be null");
        }
    }
}
