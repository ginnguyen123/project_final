package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.media.MediaDTO;
import cg.dto.productImport.ProductImportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String code;

    @NotEmpty
    private BigDecimal price;

    private String description;

    private MediaDTO productAvatarDTO;

    private BrandDTO brandDTO;

    private CategoryDTO categoryDTO;

    private List<MediaDTO> mediasDTO;

    private List<ProductImportDTO> productImportsDTO;
}
