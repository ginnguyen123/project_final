package cg.model.bill;

import cg.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bill_details")
public class BillDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_titles", nullable = false)
    private String productTitle;
    @Column(name = "quantities", nullable = false)
    private Long quantity;
    @Column(name = "prices",precision = 10, scale = 0, nullable = false)
    private BigDecimal price;
    @Column(name = "total_amounts",precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Bill bill;
}
