package cg.dto.productImport;



import cg.dto.product.ProductCreReqDTO;
import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.ProductImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ProductImportCreReqDTO implements Validator {

    private Long id;

    private String size;


    private String color;


    private String code;


    private String quantity;


    private String productStatus;

    private ProductDTO productDTO;//productCrReqDTO

    public ProductImport toProductImport(){
        return new ProductImport()
                .setId(id)
                .setSize(ESize.valueOf(size))
                .setColor(EColor.valueOf(color))
                .setCode(code)
                .setQuantity(Long.valueOf(quantity))
                .setProductStatus(EProductStatus.valueOf(productStatus))
                .setProduct(productDTO.toProduct())
                ;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return ProductImportCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductImportCreReqDTO productImportCreReqDTO = (ProductImportCreReqDTO) target;

        String size = productImportCreReqDTO.getSize();
        String color = productImportCreReqDTO.getColor();
        String code = productImportCreReqDTO.getCode();
        String quantity = productImportCreReqDTO.getQuantity();
        String productStatus = productImportCreReqDTO.getProductStatus();
//        ProductCreReqDTO productCreReqDTO = productImportCreReqDTO.getProductCre();

        if (size.isEmpty()){
            errors.reject("size.null" , "Product size must not be null");
        }
        if (color.isEmpty()){
            errors.reject("color.null" , "Product color must not be null");
        }
        if (code.isEmpty()){
            errors.reject("code.null" , "Product code must not be null");
        }
        if (productStatus.isEmpty()){
            errors.reject("productStatus.null" , "Product productStatus must not be null");
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

//        if (productCreReqDTO.getId() != null) {
//            errors.rejectValue("productId", "productId.null", "Product's id must not be null");
//        }





    }
//
//    public ProductImport toProductImport() {
//        return new ProductImport()
////                .setId(id)
//                .setSize(size)
//                .setColor(color)
//                .setQuantity(quantity)
//                .setProductStatus(productStatus)
////                .setProduct(product)
//                ;
//
//
//    }
}
