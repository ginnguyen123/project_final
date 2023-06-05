package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String code;

    private BigDecimal price;

    private String description;

    private String url;

    private BrandDTO brand;

    private CategoryDTO category;

}
