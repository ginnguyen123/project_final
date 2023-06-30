package cg.service.discount.request;


import cg.model.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class DiscountCreateRequest {
    private Long id;
    @NotNull(message = "The name is required")
    @NotEmpty(message = "The name is not empty")
    private String name;
    @NotNull(message = "The percent is required")
    private Long percent;
    @NotNull(message = "The start day is required")
    @Pattern(regexp = "^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd")
    private String startDate;
    @NotNull(message = "The end day is required")
    @Pattern(regexp = "^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd")
    private String endDate;
    public Discount toDiscount(){
        return new Discount()
                .setId(id)
                .setName(name)
                .setDiscount(percent);
    }
}
