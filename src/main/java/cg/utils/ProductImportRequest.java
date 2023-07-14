package cg.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private List<Long> idProducts;
}
