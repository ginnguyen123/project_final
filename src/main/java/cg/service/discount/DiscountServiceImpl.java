package cg.service.discount;

import cg.model.discount.Discount;
import cg.model.product.Product;
import cg.repository.DiscountRepository;
import cg.repository.ProductRepository;
import cg.service.discount.request.DiscountCreateRequest;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiscountServiceImpl implements IDiscountService{

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUtils appUtils;

    @Override
    public List<Discount> findAll() {
        return null;
    }

    @Override
    public List<Discount> getAllByDeletedIsFalse() {
        return discountRepository.getAllByDeletedIsFalse();
    }

    @Override
    public List<Discount> getAllByDeletedIsTrue() {
        return discountRepository.getAllByDeletedIsTrue();
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    public Optional<Discount> findDiscountByIdAndDeletedIsFalse(Long id) {
        return discountRepository.findDiscountByIdAndDeletedIsFalse(id);
    }

    @Override
    public Optional<Discount> findDiscountByProducts(Product product) {
        return discountRepository.findDiscountByProducts(product);
    }

    @Override
    public Discount save(Discount discount) {
        List<Product> products = discount.getProducts();
        productRepository.saveAll(products);
        return discountRepository.save(discount);
    }

    @Override
    public void delete(Discount discount) {
        discount.setDeleted(true);
        save(discount);
    }

    @Override
    public void deleteById(Long id) {
        Discount discount = findById(id).get();
        delete(discount);
    }

    @Override
    public Discount create(DiscountCreateRequest createRequest) {
        createRequest.setId(null);
        Date startDay = appUtils.stringToDate(createRequest.getStartDate());
        Date endDay = appUtils.stringToDate(createRequest.getEndDate());
        Discount discount = createRequest.toDiscount();
        discount.setStartDate(startDay);
        discount.setEndDate(endDay);
        discountRepository.save(discount);
        return discount;
    }

}
