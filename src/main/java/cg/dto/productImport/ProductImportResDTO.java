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
//@AllArgsConstructor
@NoArgsConstructor
public class ProductImportResDTO {
//    private Long productid;
//    private String size;
//    private String color;
//    private String productStatus;
//    private Long quantity;

    private Long productId;
    private ESize size;
    private EColor color;
    private EProductStatus productStatus;
    private Long quantity;

    public ProductImportResDTO(Long productId, ESize size, EColor color, EProductStatus productStatus, Long quantity) {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.productStatus = productStatus;
        this.quantity = quantity;
    }

}
