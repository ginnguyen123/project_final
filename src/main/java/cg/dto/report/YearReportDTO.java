package cg.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class YearReportDTO {
    private int year;
    private BigDecimal totalAmount;
    private Long count;
}
