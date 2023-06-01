package cg.dto.productImport;

import cg.dto.product.ProductCreDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductImportCreDTO { //implements Validator

//    private Long id;

    @NotEmpty(message = "Size can't empty")
    private String size;

    @NotEmpty(message = "Color can't empty")
    private String color;

    @NotEmpty(message = "Code can't empty")
    private String code;

    private Long quantity;

    @NotEmpty(message = "Status can't empty")
    private String productStatus;

    private ProductCreDTO productCreDTO;



//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//
//    }
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
