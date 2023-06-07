package cg.service.bill;

import cg.model.bill.Bill;
import cg.service.IGeneralService;

import java.util.List;

public interface IBillService extends IGeneralService <Bill,Long> {
    List<Bill> findAllByDeletedFalse();
}
