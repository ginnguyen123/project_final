package cg.model.product;


import cg.dto.product.ProductCreResDTO;
import cg.dto.product.ProductDTO;
import cg.dto.product.ProductResDTO;
import cg.model.BaseEntity;
import cg.model.brand.Brand;
import cg.model.category.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cg.model.media.Media;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_media_id", referencedColumnName = "id")
    private List<Media> productAvatarList;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImport> productImports;


    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setAvatar(productAvatar.toMediaDTO())
                .setMedias(productAvatarList.stream().map(i->i.toMediaDTO()).collect(Collectors.toList()))
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
                .setAvarta(productAvatar.toMediaDTO())
                .setMedias(productAvatarList.stream().map(i -> i.toMediaDTO()).collect(Collectors.toList()))
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryDTO());
    }

    public ProductCreResDTO toProductCreResDTOByCategory() {
        return new ProductCreResDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setDescription(description)
                .setAvarta(productAvatar.toMediaDTO());
    }

    public ProductResDTO toProductResDTO() {
        return new ProductResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setBrandName(brand.getName())
                .setCategoryName(category.getName())
                .setImages(productAvatarList.stream().map(item->item.toMediaDTO()).collect(Collectors.toList()))
//                .setProductImportDTOList(productImports.stream().map(item->item.toProductImportDTOWithSizeColor()).collect(Collectors.toList()))
                ;
    }
}

