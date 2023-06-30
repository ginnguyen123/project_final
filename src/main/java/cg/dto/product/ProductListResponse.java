package cg.dto.product;

import cg.dto.media.MediaDTO;
import cg.model.brand.Brand;
import cg.model.media.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductListResponse {
    private Long id;
    private String code;

    private MediaDTO avatar;

    private String title;

    private BigDecimal price;

    private String categoryName;

    public ProductListResponse(Long id, String code, Media avatar, String title, BigDecimal price, String categoryName) {
//        String strPrice = String.valueOf(price.longValue());
        MediaDTO mediaDTO = avatar.toMediaDTO();
        this.id = id;
        this.code = code;
        this.avatar = mediaDTO;
        this.title = title;
        this.price = price;
        this.categoryName = categoryName;
    }
}
