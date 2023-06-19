package cg.service.products;

import cg.dto.product.*;
import cg.exception.DataInputException;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Product> findProductsByCategoryWithLimit(Long idCategory) {
        return productRepository.findProductsByCategoryWithLimit(idCategory);
    }

    @Override
    public List<Product> findAllByDeletedFalse() {
        return productRepository.findAllByDeletedFalse();
    }

    @Override
    public List<Product> findProductWithSorting(String field) {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,field));
    }


    @Override
    public Page<ProductListResponse> findProductWithPaginationAndSortAndSearch(String search, Pageable pageable) {
        return productRepository.findAllWithSearch(search, pageable);
    }

//    @Override
//    public Page<Product> findProductWithPagination(int offset, int pageSize) {
//        Page<Product> productPage = productRepository.findAll(PageRequest.of(offset, pageSize));
//        return productPage;
//    }

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

    @Override
    public ProductUpdaResDTO update(ProductUpdaReqDTO productUpdaReqDTO) {
        Optional<Product> productOp = findById(productUpdaReqDTO.getId());
        if (!productOp.isPresent()){
            throw new DataInputException("Product not exist");
        }
        Product product = productOp.get();

        Optional<Brand> brandOptional = brandRepository.findById(productUpdaReqDTO.getId());
        if (!brandOptional.isPresent()){
            throw new DataInputException("Brand not exist!");
        }
        Brand brand = brandOptional.get();
        product.setBrand(brand);

        Discount discount = null;

        Optional<Discount> discountOptional =  discountRepository.findById(productUpdaReqDTO.getDiscountId());
        if (discountOptional.isPresent()){
            discount = discountOptional.get();
        }

        Optional<Category> categoryChildOp = categoryRepository.findById(productUpdaReqDTO.getCategoryId());
        if (!categoryChildOp.isPresent()){
            Category category = categoryRepository.findById(productUpdaReqDTO.getCategoryParentId()).get();
            product.setCategory(category);
        }else {
            Category categoryChild = categoryChildOp.get();
            product.setCategory(categoryChild);
        }
        product.setTitle(productUpdaReqDTO.getTitle());
        product.setPrice(BigDecimal.valueOf(Long.parseLong(productUpdaReqDTO.getPrice())));
        product.setDescription(productUpdaReqDTO.getDescription());
        save(product);

        if (discount != null){
            List<Product> productList = discount.getProducts();
            productList = productList.stream().filter(i -> i.getId() != product.getId()).collect(Collectors.toList());
            productList.add(product);
            discount.setProducts(productList);
            discountRepository.save(discount);
        }

        return product.toProductUpdaResDTO();
    }
}
