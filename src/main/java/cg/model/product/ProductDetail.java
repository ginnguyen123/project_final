package cg.model.product;

import cg.model.BaseEntity;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private ESize size;

    private EColor color;

    private EProductStatus status;

    @Column(name = "quantites", nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @OneToMany
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private List<ProductMedia> media;
}
