package cg.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportDTO {

    private BigDecimal totalAmount;
    private Long count;

    public ReportDTO(BigDecimal totalAmount, Long count) {
        this.totalAmount = totalAmount;
        this.count = count;
    }
}
