package cg.model.brand;


import cg.dto.brand.BrandCreResDTO;
import cg.dto.brand.BrandDTO;
import cg.model.BaseEntity;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public BrandDTO toBrandDTO(){
        return new BrandDTO()
                .setId(id)
                .setName(name);
    }

    public BrandCreResDTO toBrandCreResDTO(){
        return new BrandCreResDTO()
                .setName(name);
    }
}
