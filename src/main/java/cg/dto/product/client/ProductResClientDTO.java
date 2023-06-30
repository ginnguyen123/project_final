package cg.dto.product.client;

import cg.dto.brand.BrandDTO;
import cg.dto.category.client.CategoryClientDTO;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.media.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResClientDTO {
    private Long id;
    private String title;
    private String code;
    private String price;
    private Long priceDiscount;
    private Long percent;
    private String avatar;
    private String brand;
    private CategoryClientDTO category;

    public ProductResClientDTO(Long id, String title, String code, BigDecimal price, Discount discount, Media avatar, Brand brand, Category category){
        Long pricePercent = price.multiply(BigDecimal.valueOf(discount.getDiscount())).divide(BigDecimal.valueOf(100)).longValue();
        Long priceDiscount = price.subtract(BigDecimal.valueOf(pricePercent)).longValue();
        this.id = id;
        this.title = title;
        this.code = code;
        this.price = String.valueOf(price.longValue());
        this.percent = discount.getDiscount();
        this.priceDiscount = priceDiscount;
        this.avatar = avatar.getFileUrl();
        this.brand = brand.getName();
        this.category = category.toCategoryClientDTO();
    }
}
