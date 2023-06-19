package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.media.MediaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

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
    private BrandDTO brand;
    private CategoryDTO category;
    private MediaDTO avarta;
    private List<MediaDTO> medias;
    public ProductCreResDTO(Long id, String title, BigDecimal price, String description, MediaDTO avarta) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.avarta = avarta;
        this.description = description;
    }




}
