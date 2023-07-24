package cg.api;


import cg.dto.report.*;
import cg.exception.DataInputException;
import cg.service.bill.IBillService;
import cg.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {

    @Autowired
    IBillService billService;

    @Autowired
    ICartService cartService;

    @GetMapping("/day/{day}")
    public ResponseEntity<?> getReportOfDay(@PathVariable String day) {

        ReportDTO report = cartService.getReportOfDay(day);
        if (report == null) {
            throw new DataInputException("Ngày " + day + " chưa có doanh thu!");
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/bestSeller")
    public ResponseEntity<?> getBestSeller(Pageable pageable) {
        List<ProductReportDTO> bestSellerDTOS = cartService.getBestSeller(pageable);
        return new ResponseEntity<>(bestSellerDTOS, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<?> getReportByYear(@PathVariable int year) {
        List<YearReportDTO> reportYear = cartService.getReportByYear(year);
        if (reportYear.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reportYear, HttpStatus.OK);
    }


    @GetMapping("/month/{year}-{month}")
    public ResponseEntity<?> getReportByMonth(@PathVariable int year,@PathVariable int month) {
        YearReportDTO reportMonth = cartService.getReportByMonth(year, month);
        if (reportMonth == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reportMonth, HttpStatus.OK);
    }


    @GetMapping("/day/{startDay}/{endDay}")
    public ResponseEntity<?> getReportFromDayToDay(@PathVariable String startDay, @PathVariable String endDay) {
        String[] startDayArray = startDay.split("-");
        String[] endDayArray = endDay.split("-");

        int startDayTemp = Integer.parseInt(startDayArray[startDayArray.length - 1]) - 1;
        if (startDayTemp < 10)
            startDayArray[startDayArray.length - 1] = "0" + startDayTemp;
        else
            startDayArray[startDayArray.length - 1] = String.valueOf(startDayTemp);

        startDay = String.join("-", startDayArray);

        int endDayTemp = Integer.parseInt(endDayArray[endDayArray.length - 1]) + 1;
        if (endDayTemp < 10)
            endDayArray[endDayArray.length - 1] = "0" + endDayTemp;
        else
            endDayArray[endDayArray.length - 1] = String.valueOf(endDayTemp);

        endDay = String.join("-", endDayArray);

        List<DayToDayReportDTO> report = cartService.getReportFromDayToDay(startDay, endDay);

        if (report.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/unmarketable")
    public ResponseEntity<?> getTop5ProductUnMarketTableCurrentMonth(Pageable pageable) {
        List<ProductReportDTO> reportProductDTOS = cartService.getTop5ProductUnMarketTableCurrentMonth(pageable);
        return new ResponseEntity<>(reportProductDTOS, HttpStatus.OK);
    }

}
