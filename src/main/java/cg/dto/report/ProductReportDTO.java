package cg.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductReportDTO {
    private Long idProduct;
    private String fileUrl;
    private String title;
    private Long quantity;
    private BigDecimal totalAmount;

    public ProductReportDTO(Long idProduct, String fileUrl, String title, Long quantity, BigDecimal totalAmount) {
        this.idProduct = idProduct;
        this.fileUrl = fileUrl;
        this.title = title;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }
}
