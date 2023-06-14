package cg.dto.category;

import cg.model.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreReqDTO {

    private Long id;
//    @Length(min = 2, message = "Minimum length 2 characters")
    private String name;
    private Long categoryParentId;
    private String categoryParentName;
    private ECategoryStatus status;
    private MultipartFile categoryAvatar;
}
