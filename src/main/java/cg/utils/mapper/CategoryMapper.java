package cg.utils.mapper;

import cg.dto.category.CategoryDTO;
import cg.dto.product.ProductDTO;
import cg.model.category.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryDTO convertModelToDTO(Category category){
        Set<ProductDTO> productDTOS = new HashSet<>();

        category.getProducts().forEach(product -> {
            ProductDTO dto = productMapper.convertModelToDTO(product);
            productDTOS.add(dto);
        });

        return new CategoryDTO()
                .setId(category.getId())
                .setName(category.getName())
                .setProductsDTO(productDTOS)
                ;
    }
}
