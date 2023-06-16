package cg.model.product;

import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportUpResDTO;
import cg.model.BaseEntity;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.media.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_import")
public class ProductImport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ESize size;

    @Column(nullable = false)
    private EColor color;

    @Column(nullable = false)
    private String code;

    @Column(precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private LocalDate date_added;

    @Column(nullable = false)
    private EProductStatus productStatus;

    @ManyToOne
    @JoinColumn(name = "product_id" ,nullable = false)
    private Product product;


    public ProductImportDTO toProductImportDTO(){
        return new ProductImportDTO()
                .setId(id)
                .setSize(size)
                .setColor(color)
                .setCode(code)
                .setPrice(price)
                .setDate_added(date_added)
                .setQuantity(quantity)
                .setProductStatus(productStatus)
                .setProduct(product.toProductDTO())
                ;
    }

    public ProductImportCreResDTO toProductImportCreResDTO(){
        return new ProductImportCreResDTO()
                .setId(id)
                .setCode(code)
                .setColor(color.getValue())
                .setSize(size.getValue())
                .setPrice(price)
                .setQuantity(quantity)
                .setProductStatus(productStatus.getValue())
                .setProduct(product.toProductDTO())
                .setDate_added(date_added);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImport that = (ProductImport) o;
        return size == that.size && color == that.color && Objects.equals(code, that.code) && productStatus == that.productStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, color, code, productStatus);
    }

    public ProductImportUpResDTO toProductImportUpResDTO() {
        return new ProductImportUpResDTO()
                .setId(id)
                .setCode(code)
                .setColor(color.getValue())
                .setSize(size.getValue())
                .setPrice(price)
                .setQuantity(quantity)
                .setProductStatus(productStatus.getValue())
                .setProduct(product.toProductDTO())
                .setDate_added(date_added);
    }
}
