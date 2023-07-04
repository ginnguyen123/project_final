package cg.model.discount;

import cg.dto.discount.DiscountDTO;
import cg.model.BaseEntity;
import cg.model.category.Category;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discounts")
@Accessors(chain = true)
public class    Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    //discount chính là percent % (1 -> 100)
    private Long discount;
    @OneToMany(mappedBy = "discount")
    private List<Product> products;

    @Column(name = "start_date" ,nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date" ,nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "discount", fetch = FetchType.EAGER)
    private List<Category> categories;

    public DiscountDTO toDiscountDTO(){
        return new DiscountDTO()
                .setId(id)
                .setName(name)
                .setDiscount(discount);
//                .setStartDate(String.valueOf(startDate))
//                .setEndDate(String.valueOf(endDate));
    }


}
