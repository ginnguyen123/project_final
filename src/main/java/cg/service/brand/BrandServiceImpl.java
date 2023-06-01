package cg.service.brand;

import cg.model.brand.Brand;
import cg.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements IBrandService{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        return brandRepository.findBrandByName(name);
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        brand.setDeleted(true);
        brandRepository.save(brand);
    }

    @Override
    public void deleteById(Long id) {
        Brand brand = brandRepository.findById(id).get();
        brandRepository.save(brand);
    }

    @Override
    public Boolean existsBrandByName(String name) {
        return brandRepository.existsBrandByName(name);
    }
}
