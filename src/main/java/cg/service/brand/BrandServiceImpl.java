package cg.service.brand;

import cg.model.brand.Brand;
import cg.repository.BrandRepository;
import cg.service.ExistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements IBrandService, ExistService {


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
    public Boolean existsBrandById(Long id) {
        return brandRepository.existsBrandById(id);
    }

    @Override
    public List<Brand> findAllByDeletedFalse() {
        return brandRepository.findAllByDeletedFalse();
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

    @Override
    public boolean isValidService(Class<?> clazz) {
        return Objects.equals(clazz.getName(), Brand.class.getName());
    }

    @Override
    public boolean exist(Object id) {
        return brandRepository.existsById((Long) id);
    }
}
