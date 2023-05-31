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
    public List<Long> findAll() {
        return null;
    }

    @Override
    public Optional<Long> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Long save(Category category) {
        return null;
    }

    @Override
    public void delete(Category category) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
