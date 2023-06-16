package cg.utils;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProductImportRequest {

    private String keyword;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long quantityMin;

    private Long quantityMax;
}
