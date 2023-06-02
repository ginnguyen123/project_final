package cg.service.product;

import cg.dto.productImport.ProductImportCreDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import cg.repository.ProductImportRepository;
import lombok.AllArgsConstructor;
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
        return Optional.empty();
    }


    @Override
    public Boolean existById(Long id) {
        return false;
    }

    @Override
    public ProductImport save(ProductImport productImport) {
        return null;
    }

    @Override
    public void delete(ProductImport productImport) {

    }


    @Override
    public void deleteById(Long id) {

    }


    @Override
    public ProductImportDTO create(ProductImportCreDTO productImportCreDTO) {
//        ProductImport productImport = productImportMapper.convertDTOToModel(productImportCreDTO);
//
//        ProductImport result = productImportRepository.save(productImport);
//        ProductImportDTO productImportDTO = productImportMapper.convertModelToDTO(result);

        return new ProductImportDTO();
    }
}
