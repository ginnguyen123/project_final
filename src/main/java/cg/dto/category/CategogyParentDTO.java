package cg.dto.category;

import cg.dto.media.MediaDTO;
import cg.model.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategogyParentDTO {
    private Long id;

    private String name;

    private MediaDTO avatar;
    private ECategoryStatus status;
}
