package cg.dto.productImport;

import cg.dto.media.MediaDTO;
import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImportDTO {
    private Long id;
    private String size;
    private String color;
    private String code;
    private Long quantity;

    private String productStatus;

    //dto giao tiep vs nhau thong qua dto, khong dung entity o day
    //private Product product;
    private ProductDTO product;

    private List<MediaDTO> mediaDTOList;


    public ProductImport toProductImport() {
        return new ProductImport()
                .setId(id)
                .setSize(ESize.valueOf(size))
                .setColor(EColor.valueOf(color))
                .setCode(code)
                .setQuantity(quantity)
                .setProductStatus(EProductStatus.valueOf(productStatus))
                .setProduct(product.toProduct())
                ;
    }
}
