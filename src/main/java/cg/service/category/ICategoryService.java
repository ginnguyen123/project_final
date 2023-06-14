package cg.service.category;

import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.service.IGeneralService;

import java.util.List;


public interface ICategoryService extends IGeneralService<Category,Long> {
    boolean existsById(Long id);

    List<Category> findCategoriesByCategoryParentNotNull();

    List<Category> findAllByCategoryParent_Id(Long categoryParentId);

    List<Category> findAllCategoryByStatus(ECategoryStatus status);

    List<Category> findCategoriesByCategoryParent_Id(Long id);
}
