package cg.service.products;

import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DiscountRepository discountRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> findAllByDeletedFalse() {
        return productRepository.findAllByDeletedFalse();
    }

    @Override
    public Product save(Product product) {
        Brand brand = product.getBrand();
        Category category = product.getCategory();
        Media avatar = product.getProductAvatar();

        brandRepository.save(brand);
        categoryRepository.save(category);
        mediaRepository.save(avatar);

        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        product.setDeleted(true);
        save(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = findById(id).get();
        product.setDeleted(true);
        save(product);
    }
}
