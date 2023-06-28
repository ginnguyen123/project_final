package cg.api;

import cg.dto.discount.DiscountDTO;
import cg.model.discount.Discount;
import cg.service.discount.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
public class DiscountAPI {

    @Autowired
    private IDiscountService discountService;

//    @GetMapping
//    public ResponseEntity<?> getAllDiscount(){
//        List<Discount> discounts = discountService.getAllByDeletedIsFalse();
//        List<DiscountDTO> discountDTOS = discounts.stream().map(item -> item.toDiscountDTO()).collect(Collectors.toList());
//        return new ResponseEntity<>(discountDTOS, HttpStatus.OK);
//    }
}
