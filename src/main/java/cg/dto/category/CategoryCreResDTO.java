package cg.dto.category;

import cg.model.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreResDTO {

    private Long id;

    private String name;

    private CategoryDTO categoryParent;

    private List<CategoryDTO> categoryDTOS;


//    private Long categoryParentId;
//
//    private String categoryParentName;

    private ECategoryStatus status;

    public CategoryCreResDTO(Long id, String name, ECategoryStatus status,CategoryDTO categoryParent) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.categoryParent = categoryParent;
    }

    public CategoryCreResDTO(Long id, String name, ECategoryStatus status, List<CategoryDTO> categoryDTOS) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.categoryDTOS = categoryDTOS;
    }
}
