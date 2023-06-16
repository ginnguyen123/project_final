package cg.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
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
    private Long brandId;
    private String brandName;
    private Long categoryId;
    private String categoryName;
    private Long categoryParentId;
    private String categoryParentName;
    private Long discountId;
    private String discountName;

}
