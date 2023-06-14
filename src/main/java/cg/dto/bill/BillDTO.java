package cg.dto.bill;

import cg.dto.customerDTO.CustomerDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.dto.userDTO.UserDTO;
import cg.model.bill.Bill;
import cg.model.location_region.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private Long id;

    @Pattern(regexp = "[0-9]*")
    private String phone_receiver;

    private String name_receiver;
    private LocationRegionDTO locationRegion;

    @Pattern(regexp = "[0-9]*")
    private String total_amount;

    private UserDTO userDTO;
    private CustomerDTO customerDTO;

    public BillDTO(Long id, String phone_receiver, String name_receiver, String total_amount, UserDTO userDTO, LocationRegion locationRegion, CustomerDTO customerDTO) {
        this.id = id;
        this.phone_receiver = phone_receiver;
        this.name_receiver = name_receiver;
        this.total_amount = total_amount;
        this.userDTO = userDTO;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.customerDTO = customerDTO;
    }

    public Bill toBill() {
        return new Bill()
                .setId(id)
                .setPhone_receiver(phone_receiver)
                .setName_receiver(name_receiver)
                .setTotalAmount(BigDecimal.valueOf(Long.parseLong(total_amount)))
                .setUser(userDTO.toUser())
                .setCustomer(customerDTO.toCustomer())
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}
