package cg.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SizeCartResDTO {
    private Long id;

    private int numOfProducts;
}
