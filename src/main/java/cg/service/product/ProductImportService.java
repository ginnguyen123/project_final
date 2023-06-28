package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.*;
import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.repository.ProductImportRepository;
import cg.repository.ProductRepository;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.ProductImportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductImportService implements IProductImportService {

    @Autowired
    private ProductImportRepository productImportRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;


    @Override
    public List<ProductImport> findAll() {
        return productImportRepository.findAll();
    }

    @Override
    public Optional<ProductImport> findById(Long id) {
        return productImportRepository.findById(id);
    }


    @Override
    public Boolean existById(Long id) {
        return productImportRepository.existsById(id);
    }

    @Override
    public Optional<ProductImportDTO> getProductImportDTOByIdDeletedIsFalse(Long id) {
        return productImportRepository.getProductImportDTOByIdDeletedIsFalse(id);
    }


    @Override
    public Page<ProductImportDTO> pageableByKeywordAndDate(ProductImportRequest request, Pageable pageable) {
        if(request.getKeyword() !=null){
            request.setKeyword("%"+request.getKeyword()+"%");
        }
        return productImportRepository.pageableByKeywordAndDate(request, pageable);
    }


    @Override
    public List<ProductImportResDTO> findQuantityProductImportBySizeAndColor(Long productId) {
        return productImportRepository.findQuantityProductImportBySizeAndColor(productId);
    }


    @Override
    public ProductImport save(ProductImport productImport) {
        return productImportRepository.save(productImport);
    }

    @Override
    public void delete(ProductImport productImport) {
        productImport.setDeleted(true);
        productImportRepository.save(productImport);
    }


    @Override
    public void deleteById(Long id) {
        productImportRepository.deleteById(id);
    }


    @Override
    public ProductImportCreResDTO create(ProductImportCreReqDTO productImportCreReqDTO) {
        Long productId = Long.valueOf(productImportCreReqDTO.getProductId());
        Product product = findProductById(productId);

        ProductImport productImport = new ProductImport();

        EColor color = EColor.getEColor(productImportCreReqDTO.getColor());
        EProductStatus status = EProductStatus.STOCKING;
        ESize size = ESize.getESize(productImportCreReqDTO.getSize());
        BigDecimal price =BigDecimal.valueOf(Long.parseLong(productImportCreReqDTO.getPrice()));

        LocalDate date_added = LocalDate.parse(appUtils.convertLocalDateToString(LocalDate.parse(productImportCreReqDTO.getDate_added())));
        productImport.setDate_added(date_added);


        String code =null;
        if (code == null){
            code = "";
            String[] create_at = productImportCreReqDTO.getDate_added().split("-"); //[2000,09,09]
            String sizeCodes = productImportCreReqDTO.getSize();

            code = code +sizeCodes + create_at[2] + create_at[1]  ;


        }
        productImport.setColor(color);
        productImport.setSize(size);
        productImport.setCode(code);
        productImport.setPrice(price);
        productImport.setQuantity(Long.valueOf(productImportCreReqDTO.getQuantity()));
        productImport.setProductStatus(status);
        productImport.setProduct(product);

        return save(productImport).toProductImportCreResDTO();


    }

    @Override
    public ProductImportUpResDTO update(ProductImportUpReqDTO productImportUpReqDTO) {
        Optional<ProductImport>importOptional = productImportRepository.findById(productImportUpReqDTO.getId());
        if (!importOptional.isPresent()){
            throw new ResourceNotFoundException("Not found ProductImport ");
        }
        ProductImport productImport = importOptional.get();

        Long idProduct = Long.valueOf(productImportUpReqDTO.getProductId());

        if (idProduct == null){
            throw new DataInputException("No have product");
        }

        Product product = productRepository.findById(idProduct).get();

        EColor color = EColor.getEColor(productImportUpReqDTO.getColor());

        ESize size = ESize.getESize(productImportUpReqDTO.getSize());
        BigDecimal price =BigDecimal.valueOf(Long.parseLong(productImportUpReqDTO.getPrice()));
        LocalDate date_added = LocalDate.parse(appUtils.convertLocalDateToString(LocalDate.parse(productImportUpReqDTO.getDate_added())));

        productImport.setDate_added(date_added);
        productImport.setCode(productImport.getCode());
        productImport.setPrice(price);
        productImport.setQuantity(Long.valueOf(productImportUpReqDTO.getQuantity()));
        if (productImport.getQuantity()<0){
            productImport.setProductStatus(EProductStatus.OUT_STOCK);
        }else {
            productImport.setProductStatus(EProductStatus.STOCKING);
        }
        productImport.setProduct(product);
        productImport.setColor(color);
        productImport.setSize(size);
        return save(productImport).toProductImportUpResDTO();
    }

    @Override
    public List<ProductImportDTO> findAllByDeletedIsFalse() {
        return productImportRepository.findAllByDeletedIsFalse();
    }


    private Product findProductById(Long id){
        return productService.findById(id).orElseThrow(
                ()-> new  ResourceNotFoundException("Not found this product")
        );
    }
}
