package cg.dto.category;

import cg.dto.media.MediaDTO;
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
public class CategoryChildDTO {
    private Long id;
    private String name;
    private MediaDTO avatar;
    private ECategoryStatus status;

}
