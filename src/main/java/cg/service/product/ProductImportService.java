package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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
    public List<ESize> getFindAllEnumSize() {
        for (ESize esize: ESize.values()) {
            if (esize.getValue() != null) {
                return Collections.singletonList(esize);
            }
        } return null  ;

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
        Long productId = productImportCreReqDTO.getProduct().getId();
        Product product = findProductById(productId);

        ProductImport productImport = new ProductImport();

        EColor color = EColor.getEColor(productImportCreReqDTO.getColor());
        EProductStatus status = EProductStatus.getEProductStatus(productImportCreReqDTO.getProductStatus());
        ESize size = ESize.getESize(productImportCreReqDTO.getSize());
        BigDecimal price =BigDecimal.valueOf(Long.parseLong(productImportCreReqDTO.getPrice()));

        LocalDate date_added = LocalDate.parse(appUtils.convertLocalDateToString(LocalDate.parse(productImportCreReqDTO.getDate_added())));
        productImport.setDate_added(date_added);

        String code = productImportCreReqDTO.getCode();

        productImport.setColor(color);
        productImport.setSize(size);

        if (code.isEmpty() || code == null){
            code = "";
            String[] create_at = productImportCreReqDTO.getDate_added().split("-"); //[2000,09,09]
            String sizeCodes = productImportCreReqDTO.getSize();

            code = code +sizeCodes + create_at[2] + create_at[1]  ;

            productImport.setCode(code);
        }
        productImport.setPrice(price);
        productImport.setQuantity(Long.valueOf(productImportCreReqDTO.getQuantity()));
        productImport.setProductStatus(status);
        productImport.setProduct(product);

        return save(productImport).toProductImportCreResDTO();


    }

    @Override
    public ProductImportCreResDTO update(Product product ,ProductImport productImport) {
        productRepository.save(product);
        productImportRepository.save(productImport);
        return new ProductImportCreResDTO(product ,productImport);
    }

    private Product findProductById(Long id){
        return productService.findById(id).orElseThrow(
                ()-> new  ResourceNotFoundException("Not found this product")
        );
    }
}
