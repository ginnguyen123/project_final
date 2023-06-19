package cg.service.media;

import cg.model.media.Media;
import cg.model.product.Product;
import cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
public interface IUploadMediaService extends IGeneralService<Media, String> {

    Map uploadImage(MultipartFile multipartFile, Map params) throws IOException;

    Map destroyImage(String publicId, Map params) throws IOException;

    Map uploadVideo(MultipartFile multipartFile, Map options) throws IOException;

    Map destroyVideo(String publicId, Map options) throws IOException;

    List<Media> saveAll(List<Media> medias);

    List<Media> uploadAllImageAndSaveAllImage(List<MultipartFile> files, List<Media> medias);

    Media uploadImageAndSaveImage(MultipartFile file, Media media);


}
