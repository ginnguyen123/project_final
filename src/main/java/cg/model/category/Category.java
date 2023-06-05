package cg.model.category;

import cg.dto.category.CategoryCreReqDTO;
import cg.dto.category.CategoryCreResDTO;
import cg.dto.category.CategoryDTO;
import cg.model.BaseEntity;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public CategoryCreResDTO toCategoryCreResDTO(){
        return new CategoryCreResDTO()
                .setId(id)
                .setName(name)
                .setCategoryParentId(categoryParent.id)
                .setCategoryParentName(categoryParent.name);
    }

    public CategoryDTO toCategoryDTO(){
        return new CategoryDTO()
                .setId(id)
                .setName(name)
                .setCategoryParentId(categoryParent.id)
                .setCategoryParentName(categoryParent.name);
    }
}
