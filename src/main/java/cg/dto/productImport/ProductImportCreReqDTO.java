package cg.dto.productImport;

import cg.dto.product.ProductCreDTO;
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
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


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


    private Long quantity;


    private String productStatus;

    private ProductCreDTO productCreD;

    public ProductImport toProductImport(){
        return new ProductImport()
                .setId(id)
                .setSize(ESize.valueOf(size))
                .setColor(EColor.valueOf(color))
                .setCode(code)
                .setQuantity(quantity)
                .setProductStatus(EProductStatus.valueOf(productStatus))
//                .setProduct(productCreDTO.toProduct())
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
        Long quantity = productImportCreReqDTO.getQuantity();
        String productStatus = productImportCreReqDTO.getProductStatus();



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
