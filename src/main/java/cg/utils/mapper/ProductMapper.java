package cg.utils.mapper;

import cg.dto.brand.BrandDTO;
import cg.dto.category.CategoryDTO;
import cg.dto.media.MediaDTO;
import cg.dto.product.ProductCreDTO;
import cg.dto.product.ProductDTO;
import cg.dto.productImport.ProductImportDTO;
import cg.model.brand.Brand;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductImportMapper productImportMapper;

//    private void setMediaMapper(MediaMapper mediaMapper){
//        this.mediaMapper = mediaMapper;
//    }

//    private void setBrandMapper(BrandMapper brandMapper){
//        this.brandMapper = brandMapper;
//    }
//
//    private void setCategoryMapper(CategoryMapper categoryMapper){
//        this.categoryMapper = categoryMapper;
//    }
//
//    private void setProductImportMapper(ProductImportMapper productImportMapper) {
//        this.productImportMapper = productImportMapper;
//    }


    public ProductDTO convertModelToDTO(Product product){
        MediaDTO mediaDTO =  mediaMapper.convertModelToDTO(product.getProductAvatar());
        BrandDTO brandDTO = brandMapper.convertModelToDTO(product.getBrand());
        CategoryDTO categoryDTO = categoryMapper.convertModelToDTO(product.getCategory());
        List<MediaDTO> mediaDTOS = new ArrayList<>();

        product.getMedias().forEach(media -> {
            MediaDTO dto = mediaMapper.convertModelToDTO(media);
            mediaDTOS.add(dto);
        });

        List<ProductImportDTO> productImportDTOS = new ArrayList<>();

        product.getProductImports().forEach(productImport -> {
            ProductImportDTO productImportDTO = productImportMapper.convertModelToDTO(productImport);
            productImportDTOS.add(productImportDTO);
        });

        return new ProductDTO()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setCode(product.getCode())
                .setPrice(product.getPrice())
                .setDescription(product.getDescription())
                .setProductAvatarDTO(mediaDTO)
                .setBrandDTO(brandDTO)
                .setCategoryDTO(categoryDTO)
                .setMediasDTO(mediaDTOS)
                .setProductImportsDTO(productImportDTOS);
    }

    public Product convertDTOToModel(ProductDTO productDTO) {
        Media media = mediaMapper.convertDTOToModel(productDTO.getProductAvatarDTO());
        Brand brand = brandMapper.convertDTOToModel(productDTO.getBrandDTO());
        List<Media> medias = new ArrayList<>();

        productDTO.getMediasDTO().forEach(mediaDTO -> {
            Media model = mediaMapper.convertDTOToModel(mediaDTO);
            medias.add(model);
        });

        List<ProductImport> productImports = new ArrayList<>();

        productDTO.getProductImportsDTO().forEach(productImportDTO -> {
            ProductImport productImport = productImportMapper.convertDTOToModel(productImportDTO);
            productImports.add(productImport);
        });
        return new Product()
                .setId(productDTO.getId())
                .setTitle(productDTO.getTitle())
                .setCode(productDTO.getCode())
                .setPrice(productDTO.getPrice())
                .setDescription(productDTO.getDescription())
                .setProductAvatar(media)
                .setBrand(brand)
                .setMedias(medias)
                .setProductImports(productImports)
                ;
    }

    public Product convertDTOToModel(ProductCreDTO productCreDTO) {
        Media media = mediaMapper.convertDTOToModel(productCreDTO.getProductAvatarDTO());
        Brand brand = brandMapper.convertDTOToModel(productCreDTO.getBrandDTO());
        List<Media> medias = new ArrayList<>();

        productCreDTO.getMediasDTO().forEach(mediaDTO -> {
            Media model = mediaMapper.convertDTOToModel(mediaDTO);
            medias.add(model);
        });

        List<ProductImport> productImports = new ArrayList<>();

        productCreDTO.getProductImportsDTO().forEach(productImportDTO -> {
            ProductImport productImport = productImportMapper.convertDTOToModel(productImportDTO);
            productImports.add(productImport);
        });
        return new Product()
                .setTitle(productCreDTO.getTitle())
                .setCode(productCreDTO.getCode())
                .setPrice(productCreDTO.getPrice())
                .setDescription(productCreDTO.getDescription())
                .setProductAvatar(media)
                .setBrand(brand)
                .setMedias(medias)
                .setProductImports(productImports)
                ;
    }
}
