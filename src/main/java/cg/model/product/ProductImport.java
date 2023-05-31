package cg.model.product;

import cg.model.BaseEntity;
import cg.model.category.CategoryDetail;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_details")
public class ProductDetail extends BaseEntity {
//ProductImport
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "sizies", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESize size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EColor color;

    @Column(name = "codes", nullable = false)
    private String code;

    @Column(name = "quantites", nullable = false)
    private Long quantity;

    //quantiy sold
    // status -> INSTOCK or OUTSOTCK => ....

    // show ra Home => quantity - quantity_sold

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private EProductStatus productStatus;

    @OneToMany
    @JoinColumn(name = "product_media_id", referencedColumnName = "id")
    private List<ProductMedia> productMedias;
}
