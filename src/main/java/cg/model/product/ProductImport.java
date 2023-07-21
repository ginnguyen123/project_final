package cg.model.product;

import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.dto.productImport.ProductImportResDTO;
import cg.dto.productImport.ProductImportUpResDTO;
import cg.model.BaseEntity;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.media.Media;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    private ESize size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EColor color;

    @Column(nullable = false)
    private String code;

    @Column(precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantity; // sl hang nhap kho

    @Column
    private Long selled; // sl da ban

    @Column(name = "quantity_exists")
    private Long quantityExist; // sl hang con trong kho

    @Column(nullable = false)
    private LocalDate date_added;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EProductStatus productStatus;

    @ManyToOne
    @JoinColumn(name = "product_id" ,nullable = false)
    private Product product;

//    public ProductImport( ESize size, EColor color, EProductStatus eProductStatus, Long quantity, Product product) {
//        this.size = size;
//        this.product = product;
//        this.color = color;
//        this.productStatus = eProductStatus;
//        this.quantity = quantity;
//
//    }

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

//    public ProductImportResDTO toProductImportDTOWithSizeColor() {
//        return new ProductImportResDTO()
//                .setProductid(product.getId())
//                .setSize(size.getValue())
//                .setColor(color.getValue())
//                .setQuantity(quantity)
//                .setProductStatus(productStatus.getValue());
//    }

    public ProductImportResDTO toGetQuantityOfProductImportDTOBySizeAndColor() {
        return new ProductImportResDTO()
                .setProductId(product.getId())
                .setSize(size)
                .setColor(color)
                .setProductStatus(productStatus)
                .setQuantity(quantity);
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
