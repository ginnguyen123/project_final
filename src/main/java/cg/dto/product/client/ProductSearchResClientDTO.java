package cg.dto.product.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResClientDTO {
    private String keyword;
    private List<ProductResClientDTO> products;
    private Integer totalPages;
    private Integer totalElements;
}
