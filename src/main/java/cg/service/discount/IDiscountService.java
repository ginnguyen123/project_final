package cg.service.discount;

import cg.model.discount.Discount;
import cg.model.product.Product;
import cg.service.IGeneralService;

import java.util.List;

public interface IDiscountService extends IGeneralService<Discount,Long> {
    List<Discount> getAllByDeletedIsFalse();

    List<Discount> getAllByDeletedIsTrue();
}
