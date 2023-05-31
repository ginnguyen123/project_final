package cg.dto.productImport;

import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductImportCreDTO implements Validator {

    private Long id;
    private ESize size;
    private EColor color;
    private String code;
    private Long quantity;

    private EProductStatus productStatus;

    private String productCode;



    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public ProductImport toProductImport(ProductImportCreDTO productImportCreDTO) {
        return new ProductImport()
                .setId(id)
                .setSize(size)
                .setColor(color)
                .setQuantity(quantity)
                .setProductStatus(productStatus);
    }
}
