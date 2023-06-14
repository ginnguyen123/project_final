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

//    private CategoryDTO categoryParent;

    private Long categoryParentId;

    private String categoryParentName;

    private ECategoryStatus status;

    private MediaDTO categoryAvatar;

    public Category toCategory(){
        return new Category()
                .setId(id)
                .setName(name)
                .setStatus(status)
                .setCategoryAvatar(categoryAvatar.toMedia())
                ;
    }



}
