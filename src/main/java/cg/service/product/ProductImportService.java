package cg.service.product;

import cg.dto.productImport.ProductImportCreReqDTO;
import cg.dto.productImport.ProductImportCreResDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import cg.repository.ProductImportRepository;
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
        ProductImport productImport = productImportCreReqDTO.toProductImport();

       productImportRepository.save(productImport);
        ProductImportCreResDTO ProductImportCreResDTO = new ProductImportCreResDTO(productImport);

        return ProductImportCreResDTO;

    }

    @Override
    public ProductImportCreResDTO update(ProductImport productImport) {
        productImportRepository.save(productImport);
        return new ProductImportCreResDTO(productImport);
    }
}
