package cg.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductListResponse {
    private Long id;
    private String code;

    private String imageName;

    private String title;

    private BigDecimal price;

    private String categoryName;
}
