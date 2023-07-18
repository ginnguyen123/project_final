package cg.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ProductRequest {

    private LocalDate fromDate;

    private LocalDate toDate;

    private String keyword;
//
//    private String status;
}
