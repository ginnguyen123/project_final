package cg.dto.media;

import cg.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MediaDTO {

    private String id;

    private String fileName;

    private String fileFolder;

    private String fileUrl;

    private String fileType;

    private String cloudId;

    private ProductDTO productDTO;
}
