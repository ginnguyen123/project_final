package cg.service.product;

import cg.dto.productImport.ProductImportCreDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.product.ProductImport;
import cg.repository.ProductImportRepository;
import cg.utils.mapper.ProductImportMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductImportService implements IProductImportService {

    private final ProductImportRepository productImportRepository;

    private final ProductImportMapper productImportMapper;


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
    public ProductImportDTO create(ProductImportCreDTO productImportCreDTO) {
        ProductImport productImport = productImportMapper.convertDTOToModel(productImportCreDTO);

        ProductImport result = productImportRepository.save(productImport);
        ProductImportDTO productImportDTO = productImportMapper.convertModelToDTO(result);

        return productImportDTO;
    }
}
