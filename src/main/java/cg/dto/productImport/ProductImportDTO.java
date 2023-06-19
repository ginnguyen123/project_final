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
public class ProductImportDTO {
    private Long id;
    private ESize size;
    private EColor color;
    private String code;
    private LocalDate date_added;
    private BigDecimal price;
    private Long quantity;

    private EProductStatus productStatus;

    //dto giao tiep vs nhau thong qua dto, khong dung entity o day
    //private Product product;
    private ProductDTO product;

    public ProductImportDTO(Long id, ESize size, EColor color, String code, LocalDate date_added, BigDecimal price, Long quantity, EProductStatus productStatus, Product product) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.code = code;
        this.date_added = date_added;
        this.price = price;
        this.quantity = quantity;
        this.productStatus = productStatus;
        this.product = product.toProductDTO();
    }




    public ProductImport toProductImport(Product product) {
        return new ProductImport()
                .setId(id)
                .setSize(size)
                .setColor(color)
                .setCode(code)
                .setPrice(price)
                .setQuantity(quantity)
                .setDate_added(date_added)
                .setProductStatus(productStatus)
                .setProduct(product)
                ;
    }

}
