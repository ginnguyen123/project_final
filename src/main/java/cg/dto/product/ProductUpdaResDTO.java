package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.media.MediaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdaResDTO {
    private Long id;
    private String title;
    private String code;
    private String price;
    private String description;
    private String avatar;
    private List<MediaDTO> medias;
    private BrandDTO brand;
    private CategoryDTO category;
}
