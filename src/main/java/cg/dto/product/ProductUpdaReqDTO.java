package cg.dto.product;

import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProductUpdaReqDTO {
    private Long id;
    @NotEmpty(message = "The title is required")
    @NotNull(message = "The title is required")
    private String title;
    @NotEmpty(message = "The price is required")
    @NotNull(message = "The price is required")
    @Pattern(regexp = "^[0-9]*$", message = "The price is number")
    private String price;
    private String description;
    @NotNull(message = "Brand's value void")
    private Long brandId;
    private String brandName;
    @NotNull(message = "Category's value void")
    private Long categoryId;
    private String categoryName;
    @NotNull(message = "Category parent's value void")
    private Long categoryParentId;
    private String categoryParentName;
    private Long discountId;
    private String discountName;

}
