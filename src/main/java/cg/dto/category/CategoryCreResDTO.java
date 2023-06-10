package cg.dto.category;

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
public class CategoryCreResDTO {

    private Long id;

    private String name;

    private CategoryDTO categoryParent;


//    private Long categoryParentId;
//
//    private String categoryParentName;

    private ECategoryStatus status;
}
