package cg.dto.productImport;

import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductImportUpReqDTO implements Validator {

    private Long id;
    private String size;


    private String color;


    private String price;

    private String date_added;

    private String quantity;


    private String productId;

    public ProductImport toProductImport(Product product){

        return new ProductImport()
                .setId(id)
                .setSize(ESize.valueOf(size))
                .setColor(EColor.valueOf(color))
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setDate_added(LocalDate.parse((date_added)))
                .setQuantity(Long.valueOf(quantity))
                .setProduct(product)
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductImportUpReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductImportUpReqDTO productImportUpReqDTO = (ProductImportUpReqDTO) target;

        String size = productImportUpReqDTO.getSize();
        String color = productImportUpReqDTO.getColor();
        String price = productImportUpReqDTO.getPrice();
        String quantity = productImportUpReqDTO.getQuantity();

        String product = productImportUpReqDTO.getProductId();

        if (size.isEmpty()) {
            errors.reject("size.null", "Product size must not be null");
        }
        if (color.isEmpty()) {
            errors.reject("color.null", "Product color must not be null");
        }

        if (price != null && price.length() > 0) {
            if (!price.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("price", "price.number", "Price must be a number");
            }
            if (price.length() > 8) {
                errors.rejectValue("price", "price.max", "The maximum product price is ten million VND.");
            }
            if (price.length() < 5) {
                errors.rejectValue("price", "price.min", "The minimum product price is tem thousand VND.");
            }


            if (quantity != null && quantity.length() > 0) {
                if (!quantity.matches("(^$|[0-9]*$)")) {
                    errors.rejectValue("quantity", "quantity.number", "Quantity must be a number");

                }
                if (Long.parseLong(quantity) < 0) {
                    errors.rejectValue("quantity", "quantity.min", "The minimum product quantity is 0");

                }
                if (quantity.length() > 3) {
                    errors.rejectValue("quantity", "quantity.max", "The quantity of the product is not more than 100.");
                }
            } else {
                errors.rejectValue("quantity", "quantity.null", "Quantity must not be null");
            }

            if (product != null && product.length() > 0) {
                if (!product.matches("(^$|[0-9]*$)")) {
                    errors.rejectValue("productId", "productId.number", "Product's id must be a number");
                }
            } else {
                errors.rejectValue("id", "id.null", "Product's id must not be null");
            }


        }


    }
}
