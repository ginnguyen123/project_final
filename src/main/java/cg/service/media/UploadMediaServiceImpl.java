package cg.service.media;

import cg.dto.media.MediaDTO;
import cg.exception.DataInputException;
import cg.model.media.Media;
import cg.model.product.Product;
import cg.repository.MediaRepository;
import cg.repository.ProductRepository;
import cg.utils.UploadUtils;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UploadMediaServiceImpl implements IUploadMediaService{

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Media> findAll() {
        return null;
    }

    @Override
    public Optional<Media> findById(String id) {
        return mediaRepository.findById(id);
    }

    @Override
    public Media save(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public void delete(Media media) {
        mediaRepository.delete(media);
    }

    @Override
    public void deleteById(String id) {
        mediaRepository.deleteById(id);
    }

    @Override
    public Map uploadImage(MultipartFile multipartFile, Map options) throws IOException {
        return cloudinary.uploader().upload(multipartFile.getBytes(), options);
    }

    @Override
    public Map destroyImage(String publicId, Map options) throws IOException {
        return cloudinary.uploader().destroy(publicId, options);
    }

    @Override
    public Map uploadVideo(MultipartFile multipartFile, Map options) throws IOException {
        return cloudinary.uploader().upload(multipartFile.getBytes(), options);
    }

    @Override
    public Map destroyVideo(String publicId, Map options) throws IOException {
        return cloudinary.uploader().destroy(publicId, options);
    }

    @Override
    public List<Media> saveAll(List<Media> medias) {
        return mediaRepository.saveAll(medias);
    }

    public Media uploadImageAndSaveImage(MultipartFile file, Media media){
        try {
            Map uploadResult = uploadImage(file, uploadUtils.buildImageUploadParams(media));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");
            media.setFileName(media.getId() + "." + fileFormat);
            media.setFileUrl(fileUrl);
            media.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
            media.setCloudId(media.getFileFolder() + "/" + media.getId());
            mediaRepository.save(media);
            return media;
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public List<Media> uploadAllImageAndSaveAllImage(List<MultipartFile> files, List<Media> medias){
        for (MultipartFile file : files){
            Media media = new Media();
            save(media);
            try{
                Map uploadResult = uploadImage(file, uploadUtils.buildImageUploadParams(media));
                String fileUrl = (String) uploadResult.get("secure_url");
                String fileFormat = (String) uploadResult.get("format");
                media.setFileName(media.getId() + "." + fileFormat);
                media.setFileUrl(fileUrl);
                media.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
                media.setCloudId(media.getFileFolder() + "/" + media.getId());
                medias.add(media);
            }
            catch (IOException e){
                e.printStackTrace();
                throw new DataInputException("Upload fail");
            }
        }
        return saveAll(medias);
    }

}
