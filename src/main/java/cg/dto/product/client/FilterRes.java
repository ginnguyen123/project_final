package cg.dto.product.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterRes {
    Long minPrice;
    Long maxPrice;
    String color;
    String size;

}
