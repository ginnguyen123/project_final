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
public class CategoryService implements ICategory{

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
