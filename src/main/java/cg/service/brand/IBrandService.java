package cg.service.brand;

import cg.model.brand.Brand;
import cg.service.IGeneralService;

import java.util.Optional;

public interface IBrandService extends IGeneralService<Brand, Long> {

    Optional<Brand> findBrandByName(String name);

    Boolean existsBrandByName(String name);

    Boolean existsBrandById(Long id);

}
