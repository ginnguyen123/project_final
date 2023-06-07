package cg.dto.product;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.model.brand.Brand;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreReqDTO implements Validator {

    private Long id;
    @NotEmpty(message = "The title is required")
    @NotNull(message = "The title is required")
    private String title;

    private String code;

    @NotEmpty(message = "The price is required")
    @NotNull(message = "The price is required")
    @Pattern(regexp = "^[0-9]*$")
    private String price;
    private String description;
    private Long brandId;
    private String brandName;
    private Long categoryId;
    private MultipartFile avatar;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreReqDTO productCreReqDTO = (ProductCreReqDTO) target;
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setCode(code)
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setDescription(description);
    }
}
