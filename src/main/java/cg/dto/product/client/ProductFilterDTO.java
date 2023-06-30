package cg.dto.product.client;


import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDTO {
    private Long id;
    private String title;
    private String brandName;
    private String color;
    private String size;
    private String categoryName;
    private String price;
    private String discountName;

//    'ProductFilterDTO(  java.lang.Long,
//                        java.lang.String,
//                        java.lang.@javax.validation.constraints.NotEmpty @javax.validation.constraints.NotNull String,
//                        cg.model.enums.EColor,
//                        cg.model.enums.ESize,
//                        java.lang.String,
//                        java.math.BigDecimal,
//                        java.lang.String)'
    public ProductFilterDTO(Long id,
                            String title,
                            Brand brand,
                            EColor color,
                            ESize size,
                            Category category,
                            BigDecimal price,
                            Discount discount){
        this.id = id;
        this.title = title;
        this.brandName = brand.getName();
        this.categoryName = category.getName();
        this.color = color.toString();
        this.size = size.toString();
        this.price = price.toString();
        this.discountName = discount.getName();
    }
}
