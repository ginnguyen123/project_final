package cg.model.product;

import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
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

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private EProductStatus productStatus;

    @ManyToOne
    @JoinColumn(name = "product_id" ,nullable = false)
    private Product product;


    public ProductImportDTO toProductImportDTO(){
        return new ProductImportDTO()
                .setId(id)
                .setSize(size.getValue())
                .setColor(color.getValue())
                .setCode(code)
                .setQuantity(quantity)
                .setProductStatus(productStatus.getValue())
                .setProduct(product.toProductDTO())
                ;
    }

    public ProductImportCreResDTO toProductImportCreResDTO(){
        return new ProductImportCreResDTO()
                .setId(id)
                .setCode(code)
                .setColor(color.getValue())
                .setSize(size.getValue())
                .setQuantity(quantity)
                .setProductStatus(productStatus.getValue())
                .setProductDTO(product.toProductDTO());
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
}
