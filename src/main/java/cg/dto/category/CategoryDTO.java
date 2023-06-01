package cg.dto.category;

import cg.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private CategoryDTO categoryParent;

    private Set<ProductDTO> productsDTO;
}
