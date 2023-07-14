package cg.service.products;

import cg.dto.product.*;
import cg.dto.product.client.FilterRes;
import cg.dto.product.client.ProductResClientDTO;
import cg.dto.product.client.ProductSearchResClientDTO;
import cg.exception.DataInputException;
import cg.model.brand.Brand;
import cg.model.category.Category;
import cg.model.discount.Discount;
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
        List<ProductResClientDTO> dtoList = productPage.getContent().stream().map(i -> i.toProductResClientDTO()).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public ProductSearchResClientDTO findAllByKeyWordSearch(String keyword, Pageable pageable) {
        ProductSearchResClientDTO searchResClient = new ProductSearchResClientDTO();
        if (keyword.isEmpty())
            return searchResClient;
        LocalDate today = LocalDate.now();
        String kw = "%" + keyword + "%";
        Page<Product> idPage = productRepository.findAllBySearchFromClient(kw,today,keyword,pageable);
        if (idPage.getContent().size() == 0)
            return searchResClient;
        System.out.println(idPage.getContent());
        List<ProductResClientDTO> productResClientDTOS = idPage.getContent().stream().map(i -> i.toProductResClientDTO()).collect(Collectors.toList());
        searchResClient.setProducts(productResClientDTOS);
        searchResClient.setTotalPages(idPage.getTotalPages());
        searchResClient.setTotalElements((int) idPage.getTotalElements());
        searchResClient.setKeyword(keyword);
        return searchResClient;
    }

    @Override
    public  List<ProductResClientDTO> findAllByCategoryFilter(Long id, FilterRes filter, Pageable pageable) {
        LocalDate localDate = LocalDate.now();
        List<List<Long>> minMaxPrices = filter.getMinMax();
        List<String> colors = filter.getColors();
        List<String> sizes = filter.getSizes();
        Integer page = pageable.getPageNumber();
        Integer pageSize = pageable.getPageSize();
        String field = "";
        String sortBy = "";

        Optional<Category> categoryOp = categoryRepository.findById(id);
        if (!categoryOp.isPresent()) {
            throw new DataInputException(AppConstant.ENTITY_NOT_EXIT_ERROR);
        }

        StringBuffer strBb = new StringBuffer();

        strBb.append("SELECT prod.id " +
                "FROM products AS prod " +
                "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
                "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
                "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
                "WHERE prod.deleted = 0 " +
                "AND prod.category_id = :id " +
                "AND imp.quantity > 0 " +
                "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) ");

        if (colors.size() != 0)
            strBb.append("AND imp.color IN :colors ");
        if(sizes.size() != 0)
            strBb.append("AND imp.size IN :sizes ");

        //check mảng 2 chiều [[min, max]] (mảng 2xn) để nối chuỗi vào câu query
        if (minMaxPrices.size() != 0){
            strBb.append(" AND (");
            for (int i = 0; i < minMaxPrices.size(); i++){
                strBb.append("prod.prices BETWEEN :min");
                strBb.append(i);
                strBb.append(" AND :max");
                strBb.append(i);
                strBb.append(" ");
                if (i < minMaxPrices.size() - 1)
                    strBb.append(" OR ");
            }
            strBb.append(" ) ");
        }
        strBb.append(" GROUP BY prod.id ");

        if (pageable.getSort().isSorted()){
            String strSort = pageable.getSort().toString();
            pageable.getSort().ascending().toString();
            field = strSort.split(":")[0];
            sortBy = strSort.split(":")[1].trim();
            if(field.equals("bestseller")){
                strBb.append("ORDER BY COUNT(imp.selled) ");
            }
            else if (field.equals("inventory")) {
                strBb.append("ORDER BY COUNT(imp.quantity_exists) ");
            } else {
                strBb.append("ORDER BY prod.");
                strBb.append(field);
            }
            strBb.append(" ");
            strBb.append(sortBy);
            strBb.append("  ");
        }
//        phân trang
        strBb.append("LIMIT :page,:pageSizes ");

        System.out.println("------------------------------------------------------");
        System.out.println(strBb);

        Query query = entityManager.createNativeQuery(strBb.toString());
        query.setParameter("id", id);
        query.setParameter("today", localDate);
        query.setParameter("page", page);
        query.setParameter("pageSizes", pageSize);

        if (colors.size() != 0)
            query.setParameter("colors",colors);
        if (sizes.size() != 0)
            query.setParameter("sizes", sizes);

        if (minMaxPrices.size() != 0){
            for (int i = 0; i < minMaxPrices.size(); i++){
                Long min = minMaxPrices.get(i).get(0);
                Long max = minMaxPrices.get(i).get(1);
                String paraMin = "min" + i;
                String paraMax = "max" + i;
                query.setParameter(paraMin, min);
                query.setParameter(paraMax, max);
            }
        }
        List<Product> products = new ArrayList<>();
        List<?> listQuery =  query.getResultList();
        if(listQuery.size() == 0){
            return new ArrayList<>();
        }
        for (int i = 0; i<listQuery.size(); i++){
            Product product = productRepository.getById(Long.parseLong(String.valueOf(listQuery.get(i))));
            products.add(product);
        }
        if (products.size() == 0)
            return null;
        List<ProductResClientDTO> list = products.stream().map(i -> i.toProductResClientDTO()).collect(Collectors.toList());
        return list;
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
