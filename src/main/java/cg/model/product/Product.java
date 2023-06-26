package cg.model.product;


import cg.dto.category.CategoryChildDTO;
import cg.dto.product.ProductCreResDTO;
import cg.dto.product.ProductDTO;
import cg.dto.product.ProductResDTO;
import cg.dto.product.ProductUpdaResDTO;
import cg.dto.productImport.ProductImportResDTO;
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
import org.thymeleaf.spring5.processor.SpringInputFileFieldTagProcessor;

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

    @Column(columnDefinition="TEXT")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private List<ProductImport> productImports;


    public ProductDTO toProductDTO(){
        ProductDTO productDTO = new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setAvatar(productAvatar.toMediaDTO())
                .setMedias(productAvatarList.stream().map(Media::toMediaDTO).collect(Collectors.toList()))
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryChild());
        if (discount == null){
            productDTO.setDiscount(null);
        }else {
            productDTO.setDiscount(discount.toDiscountDTO());
        }
        return productDTO;
    }

    public ProductCreResDTO toProductCreResDTO(){
        return new ProductCreResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setAvatar(productAvatar.toMediaDTO())
                .setMedias(productAvatarList.stream().map(Media::toMediaDTO).collect(Collectors.toList()))
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryDTO());
    }

    public ProductCreResDTO toProductCreResDTOByCategory() {
        return new ProductCreResDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setDescription(description)
                .setAvatar(productAvatar.toMediaDTO());
    }
    public ProductResDTO toProductResDTO(List<ProductImportResDTO> quantityProductImports) {
        return new ProductResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setBrandName(brand.getName())
                .setCategoryId(category.getId())
                .setCategoryName(category.getName())
                .setImages(productAvatarList.stream().map(item -> item.toMediaDTO()).collect(Collectors.toList()))
                //xu ly truyen vao list ProductImportResDTO hoac thay doi voi mot class moi
                .setProductImportResDTOS(quantityProductImports)
//                .setProductImportResDTOS(productImports.stream().map(item->item.toProductImportDTOWithSizeColor()).collect(Collectors.toList()))
                ;
    }

    public ProductResDTO toVisitedAndRelatedProductResDTO() {
        return new ProductResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setUrlImage(productAvatar.getFileUrl())
                .setCategoryName(category.getName());
    }

    public Product(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ProductUpdaResDTO toProductUpdaResDTO(){
        String strPrice = String.valueOf(price.longValue());
        return new ProductUpdaResDTO()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setDescription(description)
                .setPrice(strPrice)
                .setAvatar(productAvatar.toMediaDTO())
                .setBrand(brand.toBrandDTO())
                .setCategory(category.toCategoryDTO())
                .setMedias(productAvatarList.stream().map(Media::toMediaDTO).collect(Collectors.toList()));
    }
}

