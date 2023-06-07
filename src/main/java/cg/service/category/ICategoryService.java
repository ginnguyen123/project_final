package cg.service.category;

import cg.model.category.Category;
import cg.service.IGeneralService;

import java.util.List;


public interface ICategoryService extends IGeneralService<Category,Long> {
    boolean existsById(Long id);

    List<Category> findAllByCategoryParent_Id(Long categoryParentId);
}
