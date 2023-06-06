package cg.service.product;

import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.repository.ProductImportRepository;
import cg.repository.ProductRepository;
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
    public ProductImportCreResDTO create(ProductDTO productDTO, ProductImportCreReqDTO productImportCreReqDTO) {
        ProductImport productImport = productImportCreReqDTO.toProductImport();// k tim dc bien productImportCreReqDTO

        productImport.setProduct(productDTO.toProduct());

       productImportRepository.save(productImport);
       ProductImportCreResDTO productImportCreResDTO = new ProductImportCreResDTO(productDTO,productImport);

        return productImportCreResDTO;

    }

    @Override
    public ProductImportCreResDTO update(ProductDTO productDTO ,ProductImport productImport) {
        productRepository.save(productDTO.toProduct());
        productImportRepository.save(productImport);
        return new ProductImportCreResDTO(productDTO ,productImport);
    }
}
