package cg.service.product;

import cg.dto.productImport.ProductImportCreDTO;
import cg.model.category.Category;
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
        return null;
    }

    @Override
    public Optional<ProductImport> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public ProductImport save(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ProductImport> findAllProductImport() {
        return null;
    }

    @Override
    public ProductImportCreDTO create(ProductImportCreDTO productImportCreDTO) {

        if (productImportCreDTO.getProductCode() == null){
            productImportCreDTO.setId(null);
        }

        ProductImport productImport = productImportCreDTO.toProductImport(productImportCreDTO);

        return null;
    }
}
