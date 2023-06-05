package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductCreResDTO {

    private Long id;
    private String title;
    private String code;
    private BigDecimal price;
    private String description;
    private String urlAvatar;
    private BrandDTO brand;
    private CategoryDTO category;
}
