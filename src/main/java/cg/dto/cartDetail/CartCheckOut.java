package cg.dto.cartDetail;

import cg.dto.cart.CartCreMiniCartReqDTO;
import cg.dto.locationRegionDTO.LocationRegionReceicer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartCheckOut {
    private Long cardId;

    private String username;

    private String receiverName;

    @Pattern(regexp = "[0-9]{10}")
    private String phone;

    private LocationRegionReceicer locationRegion;

    private List<CartCreMiniCartReqDTO> cartDetails;
    private String status;
}
