package cg.service.products;

import cg.dto.product.*;
import cg.dto.product.client.FilterRes;
import cg.dto.product.client.ProductResClientDTO;
import cg.exception.DataInputException;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.repository.*;
import cg.service.media.IUploadMediaService;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import cg.utils.ExistedInDb;
import cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService implements IProductService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private IUploadMediaService uploadMediaService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductImportRepository productImportRepository;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private ExistedInDb existedInDb;

    @Autowired
    private AppUtils appUtils;

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
        return productRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Product> findAllByDiscountTime(LocalDate date) {
        List<Long> idList = productRepository.findAllByDiscountTime(date);
        List<Product> products = productRepository.findByDeletedAndIdIn(false, idList);
        return products;
    }

    @Override
    public List<ProductResClientDTO> findAllByCategory(Long id, Pageable pageable) {
        Optional<Category> categoryOp = categoryRepository.findById(id);
        if (!categoryOp.isPresent()) {
            throw new DataInputException(AppConstant.ENTITY_NOT_EXIT_ERROR);
        }
        Page<Product> productPage = productRepository.findAllByCategoryToday(id, LocalDate.now(),pageable);
        System.out.println(productPage);
        List<ProductResClientDTO> dtoList = productPage.getContent().stream().map(i -> i.toProductResClientDTO()).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<ProductResClientDTO> findAllByCategoryFilter(Long id, FilterRes filter, Pageable pageable) {
        LocalDate localDate = LocalDate.now();
        List<List<Long>> minMaxPrices = filter.getMinMax();
        List<String> colors = filter.getColors();
        List<String> sizes = filter.getSizes();

        Optional<Category> categoryOp = categoryRepository.findById(id);
        if (!categoryOp.isPresent()) {
            throw new DataInputException(AppConstant.ENTITY_NOT_EXIT_ERROR);
        }

        StringBuffer strBb = new StringBuffer();

        strBb.append("SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, " +
                "prod.discount_id, prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id," +
                "prod.product_avatar_id " +
                "FROM products AS prod " +
                "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
                "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
                "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
                "WHERE prod.deleted = 0 " +
                "AND prod.category_id = :id " +
                "AND imp.quantity > 0 " +
                "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) " +
                "AND imp.color IN :colors " +
                "AND imp.size IN :sizes" );

        //check mảng 2 chiều [[min, max]] (mảng 2xn) để nối chuỗi vào câu query
        if (minMaxPrices.size() != 0){
            strBb.append(" AND ");
            for (int i = 0; i < minMaxPrices.size(); i++){
//                Long min = minMaxPrices.get(i).get(0);
//                Long max = minMaxPrices.get(i).get(1);
                strBb.append("prod.prices BETWEEN :min");
                strBb.append(i);
                strBb.append(" AND :max");
                strBb.append(i);
                if (i < minMaxPrices.size() - 1)
                    strBb.append(" OR ");
            }
        }
        strBb.append(" GROUP BY prod.id");
        System.out.println("------------------------------------------------------");
        System.out.println(strBb);

//        Query query = entityManager.createNativeQuery(strBb.toString());

        return null;

    }

    @Override
    public Page<ProductListResponse> findProductWithPaginationAndSortAndSearch(String search, Pageable pageable) {
        return productRepository.findAllWithSearch(search, pageable);
    }

    @Override
    public Product save(Product product) {
//        ProductListResponse productListResponse = AppUtils.mapper.map(product, ProductListResponse.class);
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
    public Product update(ProductUpdaReqDTO productUpdaReqDTO) {
        Long productId = productUpdaReqDTO.getId();
        if (!findById(productId).isPresent()) {
            return null;
        }

        Brand brand = brandRepository.findById(productUpdaReqDTO.getBrandId()).get();
        Category category = categoryRepository.findById(productUpdaReqDTO.getCategoryId()).get();

        Product product = findById(productId).get();
        product.setTitle(productUpdaReqDTO.getTitle());
        product.setPrice(BigDecimal.valueOf(Long.parseLong(productUpdaReqDTO.getPrice())));
        product.setDescription(productUpdaReqDTO.getDescription());
        product.setBrand(brand);
        product.setCategory(category);
        if (productUpdaReqDTO.getDiscountId() == null) {
            product.setDiscount(null);
        } else {
            Discount discount = discountRepository.findById(productUpdaReqDTO.getDiscountId()).get();
            product.setDiscount(discount);
        }
        save(product);
        return product;
    }

    @Override
    public List<Product> findProductsByCategoryWithLimit(Long idCategory) {
        return productRepository.findProductsByCategoryWithLimit(idCategory);
    }

    @Override
    public Product updateWithAvatar(ProductUpdaReqDTO productUpdaReqDTO, MultipartFile avatar) {
        Product product = update(productUpdaReqDTO);
//        Media oldMedia = product.getProductAvatar();
        Media newMedia = new Media();
        newMedia = mediaRepository.save(newMedia);
        Media media = uploadMediaService.uploadImageAndSaveImage(avatar, newMedia);
        product.setProductAvatar(media);
        save(product);
        return product;
    }

    @Override
    public Product updateWithMedias(ProductUpdaReqDTO productUpdaReqDTO, List<MultipartFile> medias) {
        Product product = update(productUpdaReqDTO);
        List<Media> oldMedias = product.getProductAvatarList();
        List<Media> newMedias = new ArrayList<>();
        newMedias = uploadMediaService.uploadAllImageAndSaveAllImage(medias, newMedias);
        newMedias.addAll(oldMedias);
        product.setProductAvatarList(newMedias);
        save(product);
        return product;
    }

    @Override
    public Product updateWithAvatarAndMedias(ProductUpdaReqDTO productUpdaReqDTO, MultipartFile avatar, List<MultipartFile> medias) {
        updateWithMedias(productUpdaReqDTO, medias);
        Product product = updateWithAvatar(productUpdaReqDTO, avatar);
        save(product);
        return product;
    }
}
