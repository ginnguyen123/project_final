package cg.model.category;

import cg.dto.category.CategoryCreResDTO;
import cg.dto.category.CategoryDTO;
import cg.model.BaseEntity;
import cg.model.enums.ECategoryStatus;
import cg.model.media.Media;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ECategoryStatus status;

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    @OneToOne
    @JoinColumn(name = "category_avatar_id", referencedColumnName = "id", nullable = false)
    private Media categoryAvatar;

    public CategoryCreResDTO toCategoryCreResDTO(){
        return new CategoryCreResDTO()
                .setId(id)
                .setName(name)
                .setCategoryParent(categoryParent.toCategoryDTO())
                .setStatus(status);
    }

    public CategoryDTO toCategoryDTO(){
        return new CategoryDTO()
                .setId(id)
                .setName(name)
                .setStatus(status)
                .setCategoryParentId(categoryParent.id)
                .setCategoryParentName(categoryParent.name);
    }
}
