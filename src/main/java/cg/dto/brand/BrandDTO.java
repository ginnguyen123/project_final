package cg.dto.brand;

import cg.model.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    public Brand toBrand(){
        return new Brand()
                .setId(id)
                .setName(name);
    }
}
