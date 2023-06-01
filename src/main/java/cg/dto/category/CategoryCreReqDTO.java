package cg.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreReqDTO {

    @NotNull(message = "The category's id is required")
    private Long id;
    private String name;
    @NotNull(message = "The category's name is required")
    @NotEmpty(message = "The category's name is required")
    private String categoryParentName;
}
