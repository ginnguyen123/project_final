package cg.dto.product.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterRes {
    Long minPrice;
    Long maxPrice;
    List<String> colors;
    List<String> sizes;

}
