package cg.model.discount;

import cg.model.BaseEntity;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discount")
public class Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @DecimalMax(value = "100", message = "Maximum discount is 100(%)")
    @DecimalMin(value = "0", message = "Minimum discount is 0(%)")
    @Column(nullable = false)
    private Long discount;

    @Column(name = "discount_amounts", precision = 10, scale = 0)
    private BigDecimal discountAmount;

    @OneToMany
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private List<Product> products;
}
