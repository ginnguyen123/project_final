package cg.service.discount;

import cg.model.discount.Discount;
import cg.model.product.Product;
import cg.repository.DiscountRepository;
import cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiscountServiceImpl implements IDiscountService{

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Discount> findAll() {
        return null;
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
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
}
