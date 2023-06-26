package cg.dto.category;


import cg.dto.media.MediaDTO;
import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
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

    private ECategoryStatus status;

    private MediaDTO avatar;

    private CategoryChildDTO categoryChild;

}
