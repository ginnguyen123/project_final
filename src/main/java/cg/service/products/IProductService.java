package cg.service.products;

import cg.dto.product.*;
import cg.dto.product.client.ProductResClientDTO;
import cg.model.category.Category;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.service.ExistService;
import cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
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

    List<Product> findAllByDiscountTime(LocalDate date);

    List<ProductResClientDTO> findAllByCategory(Long id,Pageable pageable);

    List<ProductResClientDTO> findAllByCategoryFilter(Long id,Long min,Long max,Pageable pageable);
}
