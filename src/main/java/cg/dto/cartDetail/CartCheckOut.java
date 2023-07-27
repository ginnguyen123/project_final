package cg.dto.cartDetail;

import cg.dto.cart.CartCreMiniCartReqDTO;
import cg.dto.locationRegionDTO.LocationRegionReceicer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartCheckOut {
    private Long cardId;
    private String username;
    private String receiverName;

    private String phone;

    private LocationRegionReceicer locationRegion;

    private List<CartCreMiniCartReqDTO> cartDetails;
    private String status;
}
