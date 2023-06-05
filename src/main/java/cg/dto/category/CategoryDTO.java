package cg.dto.category;


import cg.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryDTO {

    private Long id;

    private String name;

    private Long categoryParentId;

    private String categoryParentName;

    public Category toCategory(){
        return new Category()
                .setId(id)
                .setName(name);
    }

}
