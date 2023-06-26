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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImportCreResDTO {

    private Long id;


    private String size;


    private String color;

    private BigDecimal price;

    private String code;

    private LocalDate date_added;
    private Long quantity;


    private String productStatus;

    private ProductDTO product;

    public ProductImportCreResDTO(Product product, ProductImport productImport) {
        this.id = productImport.getId();
        this.size = String.valueOf(productImport.getSize());
        this.color = String.valueOf(productImport.getColor());
        this.price = productImport.getPrice();
        this.code = productImport.getCode();
        this.date_added = LocalDate.parse(productImport.getCode());
        this.quantity = productImport.getQuantity();
        this.productStatus = String.valueOf(productImport.getProductStatus());
        this.product = product.toProductDTO();

    }



}
