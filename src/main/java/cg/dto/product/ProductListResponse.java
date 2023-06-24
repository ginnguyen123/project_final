package cg.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductListResponse {
    private Long id;
    private String code;

    private String imageName;

    private String title;

    private BigDecimal price;

    private String categoryName;

    public ProductListResponse(Long id, String code, String imageName, String title,BigDecimal price,String categoryName) {
//        String strPrice = String.valueOf(price.longValue());
        this.id = id;
        this.code = code;
        this.imageName = imageName;
        this.title = title;
        this.price = price;
        this.categoryName = categoryName;
    }
}
