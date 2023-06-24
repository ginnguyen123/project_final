package cg.model.discount;

import cg.dto.discount.DiscountDTO;
import cg.model.BaseEntity;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private Long discount;
    @OneToMany(mappedBy = "discount", fetch = FetchType.EAGER)
    private List<Product> products;

    public DiscountDTO toDiscountDTO(){
        return new DiscountDTO()
                .setId(id)
                .setName(name)
                .setDiscount(discount);
    }
}
