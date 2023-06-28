package cg.service.category;

import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface ICategoryService extends IGeneralService<Category,Long> {
    boolean existsById(Long id);

    List<Category> findAllByDeletedIsFalse();

    List<Category> findCategoriesByCategoryParentNotNull();

    List<Category> findAllByCategoryParent_Id(Long categoryParentId);

    List<Category> findCategoriesByCategoryParentIdAndDeletedIsFalse(Category categoryParent);

    List<Category> findAllCategoryByStatus(ECategoryStatus status);

    List<Category> findCategoriesByCategoryParent_Id(Long id);

    List<Category> findAllByCategoryParentIsNull();
}
