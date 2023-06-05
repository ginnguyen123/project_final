package cg.dto.productImport;

import cg.dto.product.ProductCreDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImportCreResDTO {

    private Long id;


    private ESize size;


    private EColor color;


    private String code;


    private Long quantity;


    private EProductStatus productStatus;

    private ProductCreDTO productCreDTO;


    public ProductImportCreResDTO(ProductImport productImport) {

        this.id = productImport.getId();
        this.size = productImport.getSize();
        this.color = productImport.getColor();
        this.code = productImport.getCode();
        this.quantity = productImport.getQuantity();
        this.productStatus = productImport.getProductStatus();
//        this.productCreDTO = productCreDTO.toProductCreDTO();

    }
}
