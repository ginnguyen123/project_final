package cg.dto.product.client;

import cg.dto.brand.BrandDTO;
import cg.dto.category.client.CategoryClientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResClientDTO {
    private Long id;
    private String title;
    private String code;
    private String price;
    private Long priceDiscount;
    private Long percent;
    private String avatar;
    private String brand;
    private CategoryClientDTO category;
}
