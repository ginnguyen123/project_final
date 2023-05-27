package cg.model.product;


import cg.model.BaseEntity;
import cg.model.Brand;
import cg.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titles" , nullable = false)
    private String title;

    @Column(precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id" , referencedColumnName = "id" , nullable = false)
    private Category category;

    @OneToOne
    @JoinColumn(name = "brand_id" , referencedColumnName = "id" , nullable = false)
    private Brand brand;
}
