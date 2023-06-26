package cg.service.category;

import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryServiceService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAllByCategoryParent_Id(Long categoryParentId) {
        return categoryRepository.findAllByCategoryParent_Id(categoryParentId);
    }

    @Override
    public List<Category> findAllByCategoryParentIsNull() {
        return categoryRepository.findAllByCategoryParentIsNull();
    }

    @Override
    public List<Category> findAllCategoryByStatus(ECategoryStatus status) {
        return categoryRepository.findAllCategoryByStatus(status);
    }

    @Override
    public List<Category> findAllByDeletedIsFalse() {
        return categoryRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Category> findCategoriesByCategoryParent_Id(Long id) {
        return categoryRepository.findCategoriesByCategoryParent_Id(id);
    }


    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public List<Category> findCategoriesByCategoryParentNotNull() {
        return categoryRepository.findCategoriesByCategoryParentNotNull();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        delete(category);
    }


}
