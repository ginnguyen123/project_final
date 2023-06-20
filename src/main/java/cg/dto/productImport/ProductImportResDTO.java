package cg.dto.productImport;

import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImportResDTO {
    private Long id;
    private String size;
    private String color;
    private String productStatus;

    public ProductImportResDTO toProductImportDTOWithSizeColor() {
        return new ProductImportResDTO()
                .setId(id)
                .setSize(size)
                .setColor(color)
                .setProductStatus(productStatus);
    }

}
