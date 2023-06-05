package cg.model.product;


import cg.dto.product.ProductCreResDTO;
import cg.dto.product.ProductDTO;
import cg.model.BaseEntity;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cg.model.media.Media;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String code;

    @Column(name = "prices", precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    private String description;

    @OneToOne
    @JoinColumn(name = "product_avatar_id", referencedColumnName = "id", nullable = false)
    private Media productAvatar;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;


    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setProductAvatar(productAvatar.toMediaDTO())
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryDTO());

    }

    public ProductCreResDTO toProductCreResDTO(){

        return new ProductCreResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setUrlAvatar(productAvatar.getFileUrl())
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryDTO());
    }
}
