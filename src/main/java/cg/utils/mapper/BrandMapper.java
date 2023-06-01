package cg.utils.mapper;

import cg.dto.brand.BrandDTO;
import cg.model.brand.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandDTO convertModelToDTO(Brand brand) {
        return new BrandDTO()
                .setId(brand.getId())
                .setName(brand.getName())
                ;
    }

    public Brand convertDTOToModel(BrandDTO brandDTO) {
        return new Brand()
                .setId(brandDTO.getId())
                .setName(brandDTO.getName())
                ;
    }
}
