package cg.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DayToDayReportDTO {
    BigDecimal totalAmount;
    String day;
}
