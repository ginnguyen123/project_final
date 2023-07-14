package cg.utils;

import cg.model.product.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ProductImportRequest {

    private LocalDate fromDate;

    private LocalDate toDate;

    private String keyword;

    private String status;
}
