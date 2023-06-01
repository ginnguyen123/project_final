package cg.utils.mapper;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductImportMapper {

    @Autowired
    private ProductMapper productMapper;

    public ProductImport convertDTOToModel (ProductImportCreDTO productImportCreDTO){
        Product product = productMapper.convertDTOToModel(productImportCreDTO.getProductCreDTO());
        ESize size =  ESize.valueOf(productImportCreDTO.getSize().toUpperCase());
        EColor color = EColor.valueOf(productImportCreDTO.getColor());
        EProductStatus status = EProductStatus.valueOf(productImportCreDTO.getProductStatus());

        return new ProductImport()
                .setSize(size)
                .setColor(color)
                .setCode(productImportCreDTO.getCode())
                .setQuantity(productImportCreDTO.getQuantity())
                .setProductStatus(status)
                .setProduct(product)
                ;
    }

    public ProductImport convertDTOToModel (ProductImportDTO productImportDTO){
        Product product = productMapper.convertDTOToModel(productImportDTO.getProductDTO());
        return new ProductImport()
                .setSize(productImportDTO.getSize())
                .setColor(productImportDTO.getColor())
                .setCode(productImportDTO.getCode())
                .setQuantity(productImportDTO.getQuantity())
                .setProductStatus(productImportDTO.getProductStatus())
                .setProduct(product)
                ;
    }

    public ProductImportDTO convertModelToDTO(ProductImport productImport) {
        ProductDTO productDTO =  productMapper.convertModelToDTO(productImport.getProduct());

        return new ProductImportDTO()
                .setId(productImport.getId())
                .setSize(productImport.getSize())
                .setColor(productImport.getColor())
                .setCode(productImport.getCode())
                .setQuantity(productImport.getQuantity())
                .setProductStatus(productImport.getProductStatus())
                .setProductDTO(productDTO)
                .setDeleted(productImport.isDeleted())
                .setCreatedAt(productImport.getCreatedAt())
                .setCreatedBy(productImport.getCreatedBy())
                .setUpdateAt(productImport.getUpdateAt())
                .setUpdateBy(productImport.getUpdateBy())
                ;
    }
}
