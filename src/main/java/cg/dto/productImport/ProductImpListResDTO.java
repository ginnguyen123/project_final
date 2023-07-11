package cg.dto.productImport;

import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImpListResDTO {
    private Long id;
    private Long idProduct;
    private String size;
    private String color;
    private Long prices;
    private Long quantitiesExist;
    private Long selled;
    private String status;
    private String title;
    private String dateAdded;
    private Long quantities;


    public ProductImpListResDTO(Long id, Long idProduct, ESize size, EColor color, BigDecimal prices, Long quantities,Long quantitiesExist,
                                Long selled, EProductStatus status, String title, LocalDate dateAdded){
        String formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(dateAdded);
        this.id = id;
        this.idProduct = idProduct;
        this.size = size.getValue();
        this.color = color.getValue();
        this.prices = prices.longValue();
        this.quantities = quantities;
        this.quantitiesExist = quantitiesExist;
        this.selled = selled;
        this.status = status.getValue();
        this.title = title;
        this.dateAdded = formatter;
    }
}
