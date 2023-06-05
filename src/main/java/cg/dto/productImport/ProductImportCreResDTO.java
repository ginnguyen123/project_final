package cg.dto.productImport;

import cg.dto.product.ProductCreResDTO;
import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
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


    private ESize size;


    private EColor color;


    private String code;


    private Long quantity;


    private EProductStatus productStatus;

    private ProductDTO productDTO;

    List<ProductImportDTO> productImportDTOList;


    public ProductImportCreResDTO(ProductImport productImport) {

        this.id = productImport.getId();
        this.size = productImport.getSize();
        this.color = productImport.getColor();
        this.code = productImport.getCode();
        this.quantity = productImport.getQuantity();
        this.productStatus = productImport.getProductStatus();
        this.productDTO = productImport.toProductImportDTO().getProduct();

    }

    public ProductImport toProductImport(){
        return new ProductImport()
                .setId(id)
                .setSize(size)
                .setColor(color)
                .setCode(code)
                .setQuantity(Long.valueOf(quantity))
                .setProductStatus(productStatus)
                .setProduct(productDTO.toProduct());

    }


}
