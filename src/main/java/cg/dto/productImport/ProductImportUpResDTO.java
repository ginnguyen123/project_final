package cg.dto.productImport;

import cg.dto.product.ProductDTO;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImportUpResDTO {

    private Long id;


    private String size;


    private String color;

    private BigDecimal price;

    private String code;

    private LocalDate date_added;
    private Long quantity;


    private String productStatus;

    private ProductDTO product;



    public ProductImportUpResDTO(Product product, ProductImport productImport) {
        this.id = productImport.getId();
        this.size = String.valueOf(productImport.getSize());
        this.color = String.valueOf(productImport.getColor());
        this.price = productImport.getPrice();
        this.code = productImport.getCode();
        this.date_added = LocalDate.parse(productImport.getCode());
        this.quantity = productImport.getQuantity();
        this.product = product.toProductDTO();

    }
}
