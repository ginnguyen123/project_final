package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryChildDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.discount.DiscountDTO;
import cg.dto.media.MediaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

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

    private MediaDTO avatar;

    private List<MediaDTO> medias;

    private BrandDTO brand;

    private CategoryDTO category;

    private DiscountDTO discount;


}
