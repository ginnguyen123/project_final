package cg.service.bill;

import cg.dto.report.YearReportDTO;
import cg.model.bill.Bill;
import cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBillService extends IGeneralService <Bill,Long> {
    List<Bill> findAllByDeletedFalse();

}
