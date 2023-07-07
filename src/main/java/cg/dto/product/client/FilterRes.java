package cg.dto.product.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterRes {
    Long id;
    List<String> colors;
    List<String> sizes;
    List<List<Long>> minMax;

}
