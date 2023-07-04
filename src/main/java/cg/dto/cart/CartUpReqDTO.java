package cg.dto.cart;


import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.model.cart.Cart;
import cg.model.enums.ECartStatus;
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
public class CartUpReqDTO implements Validator {

    private Long id;
    private String name_receiver;
    private String email;
    private String phone_receiver;
    private List<CartDetailUpReqDTO> cartDetailDTOList;
    private BigDecimal totalAmount;
    private LocationRegionDTO locationRegion;
    private String status;


    public CartUpReqDTO(Long id, String name_receiver,String phone_receiver,  BigDecimal totalAmount, Long locationRegionId, ECartStatus status) {
        this.id = id;
        this.name_receiver = name_receiver;
        this.phone_receiver = phone_receiver;
        this.totalAmount = totalAmount;
        this.status = String.valueOf(status);
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
//        CartUpReqDTO cartUpReqDTO = (CartUpReqDTO) target;
//
//
//        String fullName = cartUpReqDTO.getFullName();
//        String email = cartUpReqDTO.getEmail();
//        String phone = cartUpReqDTO.getPhone();
//        String totalAmount = String.valueOf(cartUpReqDTO.getTotalAmount());
//        String locationRegion = String.valueOf(cartUpReqDTO.getLocationRegion());
//
//
//        if (fullName.isEmpty()) {
//            errors.reject("fullName.null", "Customer fullName must not be null");
//        }
//
//        if (locationRegion.isEmpty()) {
//            errors.reject("Address.null", "Address must not be null");
//        }
//
//        if (totalAmount.isEmpty()) {
//            errors.reject("date.null", "Product date must not be null");
//        }
//        if (email != null && email.length() > 0) {
//            if (!email.matches("^(.+)@(\\S+)$")) {
//                errors.rejectValue("email", "Email must be in the correct format");
//            }
//        } else {
//            errors.rejectValue("email", "email.null", "Email must not be null");
//        }
//
//        if (phone != null && phone.length() > 0) {
//            if (!phone.matches("(^$|[0-9]*$)")) {
//                errors.rejectValue("phone", "phone.number", "phone must be a number");
//            }
//            if (phone.length() == 11) {
//                errors.rejectValue("phone", "phone", "Phone number must be 10 digits");
//            }
//
//        }
    }
    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setName_receiver(name_receiver)
                .setPhone_receiver(phone_receiver)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setStatus(ECartStatus.valueOf(status))
                .setTotalAmount(totalAmount);
    }

}
