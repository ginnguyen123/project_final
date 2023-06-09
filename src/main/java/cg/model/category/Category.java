package cg.model.category;

import cg.dto.category.*;
import cg.dto.category.client.CategoryClientDTO;
import cg.model.BaseEntity;
import cg.model.discount.Discount;
import cg.model.enums.ECategoryStatus;
import cg.model.media.Media;
import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;


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
    private ECategoryStatus status;

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    @OneToOne
    @JoinColumn(name = "category_avatar_id", referencedColumnName = "id", nullable = false)
    private Media categoryAvatar;

    public CategoryCreResDTO toCategoryCreResDTO(){
        return new CategoryCreResDTO()
                .setId(id)
                .setName(name)
                .setAvatar(categoryAvatar.getFileUrl())
                .setStatus(status);
    }

    public CategoryChildDTO toCategoryChild(){
        return new CategoryChildDTO()
                .setId(id)
                .setName(name)
                .setStatus(status)
                .setAvatar(categoryAvatar.toMediaDTO());
    }

    public CategoryUpdaDTO toCategoryUpdaDTO(){
        if (categoryParent == null){
            return new CategoryUpdaDTO()
                    .setId(id)
                    .setName(name)
                    .setStatus(status)
                    .setAvatar(categoryAvatar.toMediaDTO())
                    .setCategoryChild(null);
        }else {
            return new CategoryUpdaDTO()
                    .setId(categoryParent.getId())
                    .setName(categoryParent.getName())
                    .setStatus(categoryParent.getStatus())
                    .setAvatar(categoryParent.getCategoryAvatar().toMediaDTO())
                    .setCategoryChild(toCategoryChild());
        }
    }


    public CategoryDTO toCategoryDTO(){
            return new CategoryDTO()
                    .setId(id)
                    .setName(name)
                    .setStatus(status)
                    .setAvatar(categoryAvatar.toMediaDTO())
                    .setCategoryChild(null);
    }

    public CategoryCreateRespDTO toCategoryCreateRespDTO(){
        if (categoryParent == null){
            return new CategoryCreateRespDTO()
                    .setId(id)
                    .setName(name)
                    .setStatus(status)
                    .setAvatar(categoryAvatar.toMediaDTO())
                    .setCategoryChild(null);
        }
        else {
            CategoryChildDTO categoryChild = new CategoryChildDTO(id, name, categoryAvatar.toMediaDTO() ,status);
            return new CategoryCreateRespDTO()
                    .setId(categoryParent.getId())
                    .setName(categoryParent.getName())
                    .setStatus(categoryParent.getStatus())
                    .setAvatar(categoryParent.getCategoryAvatar().toMediaDTO())
                    .setCategoryChild(categoryChild);
        }
    }

    public CategogyParentDTO toCategogyParentDTO(){
        if (categoryParent == null){
            return new CategogyParentDTO()
                    .setId(id)
                    .setName(name)
                    .setAvatar(categoryAvatar.toMediaDTO())
                    .setStatus(status);
        }
        else
            return new CategogyParentDTO()
                    .setId(categoryParent.id)
                    .setName(categoryParent.name)
                    .setAvatar(categoryParent.categoryAvatar.toMediaDTO())
                    .setStatus(categoryParent.status);
    }

    public CategoryClientDTO toCategoryClientDTO(){
        return new CategoryClientDTO(id, name, categoryAvatar.getFileUrl());
    }
}
