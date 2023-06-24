package cg.utils;

import cg.exception.DataInputException;
import cg.model.media.Media;
import cg.model.product.Product;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class UploadUtils {
    public static final String IMAGE_UPLOAD_FOLDER = "tokyo_life_product_images";

    public static final String VIDEO_UPLOAD_FOLDER = "tokyo_life_product_videos";
    public Map buildImageUploadParams(Media media) {
        if (media == null || media.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");
        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, media.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(Media media, String publicId) {
        if (media == null || media.getId() == null)
            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }
    public Map buildVideoUploadParams(Media media) {
        if (media == null || media.getId() == null)
            throw new DataInputException("Can't upload unsaved product video");

        String publicId = String.format("%s/%s", VIDEO_UPLOAD_FOLDER, media.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "video"
        );
    }

    public Map buildVideoDestroyParams(Product product, String publicId) {
        if (product == null || product.getId() == null)
            throw new DataInputException("Cannot destroy video of unknown product");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "video"
        );
    }

    public static void validateImage(MultipartFile file){
        if(!AppConstant.IMAGE_TYPES.contains(file.getContentType())){
            throw new DataInputException(AppConstant.INVALID_IMAGE_MESSAGE);
        }
    }
}
