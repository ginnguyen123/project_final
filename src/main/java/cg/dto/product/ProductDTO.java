package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.media.MediaDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String code;

    private BigDecimal price;

    private String description;

    private MediaDTO url;

    private BrandDTO brand;

    private CategoryDTO category;


    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(price)
                .setDescription(description)
                .setBrand(brand.toBrand())
                .setProductAvatar(url.toMedia())
                .setCategory(category.toCategory());

    }

}
