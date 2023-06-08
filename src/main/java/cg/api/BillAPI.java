package cg.api;

import cg.dto.bill.BillDTO;
import cg.model.bill.Bill;
import cg.repository.BillRepository;
import cg.service.bill.BillServiceImpl;
import cg.utils.AppUtils;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {
    @Autowired
    private BillServiceImpl billService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllDeleteFalse() {
        List<Bill> billList = billService.findAllByDeletedFalse();
        List<BillDTO> billDTOS = billList.stream().map(item -> item.ToBillDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    //Thieu validate o billDTO
    @PostMapping()
    public ResponseEntity<?> createBill(@Validated @RequestBody BillDTO billDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Bill newBill = new Bill();
        newBill.setPhone_receiver(billDTO.getPhone_receiver());
        newBill.setName_receiver(billDTO.getName_receiver());
        newBill.setTotalAmount(BigDecimal.valueOf(Long.parseLong(billDTO.getTotal_amount())));
        newBill.setLocationRegion(billDTO.getLocationRegion().toLocationRegion());
        newBill.setCustomer(billDTO.toBill().getCustomer());
//        Khong set user
        newBill.setUser(billDTO.toBill().getUser());
        billService.save(newBill);
        BillDTO newBillDTO = newBill.ToBillDTO();
        return new ResponseEntity<>(newBillDTO, HttpStatus.OK);
    }
}
