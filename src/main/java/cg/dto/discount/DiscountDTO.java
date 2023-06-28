package cg.dto.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DiscountDTO {
    private Long id;
    private String name;
    @DecimalMax(value = "100", message = "Maximum discount is 100(%)")
    @DecimalMin(value = "0", message = "Minimum discount is 0(%)")
    @Length(min = 0, max = 3)
    @Pattern(regexp = "[0-9]*")
    private Long discount;
    @Pattern(regexp = "[0-9]{4}-([0-9]|0[0-9]|1[0-2])-([0-9]|[0-2][0-9]|3[0-1])$")
    private String startDate;
    @Pattern(regexp = "[0-9]{4}-([0-9]|0[0-9]|1[0-2])-([0-9]|[0-2][0-9]|3[0-1])$")
    private String endDate;


}
