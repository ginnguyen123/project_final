package cg.service.products;

import cg.dto.product.*;
import cg.model.category.Category;
import cg.model.product.Product;
import cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IProductService extends IGeneralService<Product,Long> {
    List<Product> saveAll(List<Product> products);
    List<Product> findAllByDeletedFalse();

    Product update(ProductUpdaReqDTO productUpdaReqDTO);

    Product updateWithAvatar(ProductUpdaReqDTO productUpdaReqDTO, MultipartFile avatar);

    Product updateWithMedias(ProductUpdaReqDTO productUpdaReqDTO, List<MultipartFile> medias);

    Product updateWithAvatarAndMedias(ProductUpdaReqDTO productUpdaReqDTO, MultipartFile avatar,List<MultipartFile> medias);

    Page<ProductListResponse> findProductWithPaginationAndSortAndSearch(String search, Pageable pageable);

    List<Product> findProductsByCategoryWithLimit(Long idCategory);
}
