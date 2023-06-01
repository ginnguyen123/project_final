package cg.dto.productImport;

import cg.dto.product.ProductDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImportDTO {
    private Long id;
    private ESize size;
    private EColor color;
    private String code;
    private Long quantity;

    private EProductStatus productStatus;

    //dto giao tiep vs nhau thong qua dto, khong dung entity o day
    //private Product product;
    private ProductDTO productDTO;

    private boolean deleted;

    private Date createdAt;

    private Long createdBy;

    private Date updateAt;

    private Long updateBy;

    //theo nguyen tac single responsive cua SOLID thi khong chuyen dto sang entity o day
    // ma chuyen sang lop ProductMapper

//    private ProductImport toProductImport() {
//        return new ProductImport()
//                .setId(id)
//                .setSize(size)
//                .setColor(color)
//                .setCode(code)
//                .setQuantity(quantity)
//                .setProductStatus(productStatus)
//                //.setProduct(product.toProduct())
//                ;
//    }
}