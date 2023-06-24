package cg.service.products.request;

import cg.anotation.ExistInDb;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreateRequest {

    @NotBlank(message = "The title is required")
    private String title;

    @NotBlank(message = "The price is required")
    @Pattern(regexp = "^[0-9]*$", message = "The price is number")
    private String price;

    @NotBlank(message = "The description is required")
    private String description;


    @NotNull(message = "The brand is required")
    @ExistInDb(entity = Brand.class)
    private Long brandId;

    @NotBlank(message = "The brand is required")
    private String brandName;

    @NotNull(message = "The brand is required")
    @ExistInDb(entity = Category.class)
    private Long categoryId;

    @ExistInDb(entity = Discount.class)
    private Long discountId;

}
