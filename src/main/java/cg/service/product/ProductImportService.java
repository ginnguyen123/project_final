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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductImportService implements IProductImportService {

    @Autowired
    private ProductImportRepository productImportRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductService productService;


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
        Long productId = productImportCreReqDTO.getProductDTO().getId();
        Product product = findProductById(productId);

        List<ProductImport> productImports = productImportRepository.findAllByProductId(productId);

        ProductImport productImport = new ProductImport();

        EColor color = EColor.getEColor(productImportCreReqDTO.getColor());
        EProductStatus status = EProductStatus.getEProductStatus(productImportCreReqDTO.getProductStatus());
        ESize size = ESize.getESize(productImportCreReqDTO.getSize().toUpperCase());
        String code = productImportCreReqDTO.getCode();
        Long quantity = Long.valueOf(productImportCreReqDTO.getQuantity());


        Long quantityOld = productImport.getQuantity();
        
        if (size == ESize.getESize(String.valueOf(productImport.getSize())) && color == EColor.getEColor(String.valueOf(productImport.getColor()))){
            productImport.setQuantity(quantityOld + quantity);

        }else {
            productImport.setQuantity(Long.valueOf(productImportCreReqDTO.getQuantity()));
        }
        productImport.setColor(color);
        productImport.setProductStatus(status);
        productImport.setSize(size);
        productImport.setCode(code);
        productImport.setProduct(product);

        return save(productImport).toProductImportCreResDTO();

        //

//        ProductImport productImport = productImportCreReqDTO.toProductImport();
//
//
//        productImport.setProduct(product);
//
//       productImportRepository.save(productImport);
//       ProductDTO productDTO = product.toProductDTO();
//
//       ProductImportCreResDTO productImportCreResDTO = new ProductImportCreResDTO();
//       productImportCreResDTO.setProductDTO(productDTO);
//
//
//        return productImportCreResDTO;

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
