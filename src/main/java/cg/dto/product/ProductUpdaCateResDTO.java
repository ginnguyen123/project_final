package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.category.CategoryUpdaDTO;
import cg.dto.discount.DiscountDTO;
import cg.dto.media.MediaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdaCateResDTO {
    private Long id;
    private String title;
    private String code;
    private String price;
    private String description;
    private MediaDTO avatar;
    private List<MediaDTO> medias;
    private BrandDTO brand;
    private CategoryUpdaDTO category;
    private DiscountDTO discount;
}
