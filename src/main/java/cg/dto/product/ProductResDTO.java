package cg.dto.product;

import cg.dto.media.MediaDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportResDTO;
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
public class ProductResDTO {
    private Long id;
    private String title;
    private String code;
    private BigDecimal price;
    private String description;
    private String brandName;
    private String urlImage;
    private Long categoryId;
    private String categoryName;
    private List<MediaDTO> images;
    private List<ProductImportResDTO> productImportResDTOS;


    public ProductResDTO(Long id, String title, String code, BigDecimal price, String description, String brandName, String urlImage, String categoryName) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.price = price;
        this.description = description;
        this.urlImage = urlImage;
        this.categoryName = categoryName;
    }
}
