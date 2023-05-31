package cg.utils;

//import com.cloudinary.utils.ObjectUtils;
//import com.example.exception.DataInputException;
//import com.example.model.Product;
//import com.example.model.ProductMedia;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class UploadUtils {
//    public final String IMAGE_UPLOAD_FOLDER = "product_images";
//    public final String VIDEO_UPLOAD_FOLDER = "product_videos";
//
//    public Map buildImageUploadParams(ProductMedia productMedia) {
//        if (productMedia == null || productMedia.getId() == null)
//            throw new DataInputException("Unable to upload an image of an unsaved product");
//
//        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, productMedia.getId());
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "image"
//        );
//    }
//
//    public Map buildImageDestroyParams(Product product, String publicId) {
//        if (product == null || product.getId() == null)
//            throw new DataInputException("Cannot destroy image of unknown product");
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "image"
//        );
//    }
//
//    public Map buildVideoUploadParams(ProductMedia productMedia) {
//        if (productMedia == null || productMedia.getId() == null)
//            throw new DataInputException("Can't upload unsaved product video");
//
//        String publicId = String.format("%s/%s", VIDEO_UPLOAD_FOLDER, productMedia.getId());
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "video"
//        );
//    }
//
//    public Map buildVideoDestroyParams(Product product, String publicId) {
//        if (product == null || product.getId() == null)
//            throw new DataInputException("Cannot destroy video of unknown product");
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "video"
//        );
//    }
//}
