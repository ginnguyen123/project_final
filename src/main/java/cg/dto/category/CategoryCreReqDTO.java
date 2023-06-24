package cg.dto.category;

import cg.model.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreReqDTO {

    private Long id;
    @NotNull(message = "The name is required")
    @NotEmpty(message = "The name is required")
    private String name;
    private Long categoryParentId;
    private String categoryParentName;
    @NotNull(message = "The status is required")
    @NotEmpty(message = "The status is required")
    private ECategoryStatus status;

    @NotNull(message = "The category's avatar is required")
    private MultipartFile categoryAvatar;
}
