package cg.utils.mapper;

import cg.dto.media.MediaCreDTO;
import cg.dto.media.MediaDTO;
import cg.dto.product.ProductDTO;
import cg.model.media.Media;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {


    private ProductMapper productMapper;

    private void setProductMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    public MediaDTO convertModelToDTO(Media media) {
        ProductDTO productDTO = productMapper.convertModelToDTO(media.getProduct());

        return new MediaDTO()
                .setId(media.getId())
                .setFileName(media.getFileName())
                .setFileFolder(media.getFileFolder())
                .setFileUrl(media.getFileUrl())
                .setFileType(media.getFileType())
                .setCloudId(media.getCloudId())
                .setProductDTO(productDTO)
                ;
    }

    public Media convertDTOToModel(MediaDTO mediaDTO) {
        Product product = productMapper.convertDTOToModel(mediaDTO.getProductDTO());

        return new Media()
                .setId(mediaDTO.getId())
                .setFileName(mediaDTO.getFileName())
                .setFileFolder(mediaDTO.getFileFolder())
                .setFileUrl(mediaDTO.getFileUrl())
                .setFileType(mediaDTO.getFileType())
                .setCloudId(mediaDTO.getCloudId())
                .setProduct(product)
                ;
    }

    public Media convertDTOToModel(MediaCreDTO mediaCreDTO) {
        Product product = productMapper.convertDTOToModel(mediaCreDTO.getProductDTO());

        return new Media()
                .setFileName(mediaCreDTO.getFileName())
                .setFileFolder(mediaCreDTO.getFileFolder())
                .setFileUrl(mediaCreDTO.getFileUrl())
                .setFileType(mediaCreDTO.getFileType())
                .setCloudId(mediaCreDTO.getCloudId())
                .setProduct(product)
                ;
    }
}
