package cg.dto.productImport;

import cg.dto.product.ProductCreResDTO;
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

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImportCreResDTO {

    private Long id;


    private String size;


    private String color;


    private String code;


    private Long quantity;


    private String productStatus;

    private ProductDTO productDTO;

    public ProductImportCreResDTO(Product product, ProductImport productImport) {
    }


    public ProductImport toProductImport(){
        return new ProductImport()
                .setId(id)
                .setSize(ESize.getESize(size))
                .setColor(EColor.getEColor(color))
                .setCode(code)
                .setQuantity(Long.valueOf(quantity))
                .setProductStatus(EProductStatus.getEProductStatus(productStatus))
                .setProduct(productDTO.toProduct());

    }


}
