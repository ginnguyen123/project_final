package cg.api;

import cg.dto.discount.DiscountDTO;
import cg.model.discount.Discount;
import cg.service.discount.IDiscountService;
import cg.service.discount.request.DiscountCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
public class DiscountAPI {
    @Autowired
    private IDiscountService discountService;

    @GetMapping
    public ResponseEntity<?> getAllDiscount(){
        List<Discount> discounts = discountService.getAllByDeletedIsFalse();
        List<DiscountDTO> discountDTOS = discounts.stream().map(item -> item.toDiscountDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(discountDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated DiscountCreateRequest discountCreateRequest){
        System.out.println(discountCreateRequest);
        Discount discount = discountService.create(discountCreateRequest);
        return new ResponseEntity<>(discountCreateRequest,HttpStatus.CREATED);
    }
}
