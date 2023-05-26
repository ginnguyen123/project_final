package cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name" , nullable = false)
    private String productName;

    @Column(name = "product_price" , nullable = false)
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "product_media_id" , referencedColumnName = "id" , nullable = false)
    private ProductMedia productMedia;

    @ManyToOne
    @JoinColumn(name = "category_id" , referencedColumnName = "id" , nullable = false)
    private Category category;

    @OneToOne
    @JoinColumn(name = "brand_id" , referencedColumnName = "id" , nullable = false)
    private Brand brand;


}
