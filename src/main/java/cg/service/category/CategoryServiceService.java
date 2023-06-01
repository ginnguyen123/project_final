package cg.service.category;

import cg.model.category.Category;
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
<<<<<<< HEAD:src/main/java/cg/service/category/CategoryServiceService.java
        return categoryRepository.findAll();
=======
        return null;
>>>>>>> 843b4696ed0e2431b9474ace965d17e65af1b678:src/main/java/cg/service/category/CategoryService.java
    }

    @Override
    public Optional<Category> findById(Long id) {
<<<<<<< HEAD:src/main/java/cg/service/category/CategoryServiceService.java
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        category.setDeleted(true);
        categoryRepository.save(category);
=======
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Category save(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

>>>>>>> 843b4696ed0e2431b9474ace965d17e65af1b678:src/main/java/cg/service/category/CategoryService.java
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        delete(category);
    }
}
