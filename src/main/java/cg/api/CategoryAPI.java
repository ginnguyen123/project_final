package cg.api;

import cg.dto.category.CategoryCreReqDTO;
import cg.dto.category.CategoryCreResDTO;
import cg.exception.DataInputException;
import cg.model.category.Category;
import cg.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryCreReqDTO categoryCreReqDTO){
        Optional<Category> categoryOptional = categoryService.findById(categoryCreReqDTO.getId());

        if (!categoryOptional.isPresent()){
            throw new DataInputException("Category is not exist!");
        }

        Category categoryDb = categoryOptional.get();

        Category newCategory = new Category();
        newCategory.setId(null);
        newCategory.setName(categoryCreReqDTO.getCategoryParentName());
        newCategory.setCategoryParent(categoryDb);

        Category categoryRes = categoryService.save(newCategory);

        CategoryCreResDTO categoryCreResDTO = categoryRes.toCategoryCreResDTO();

        return new ResponseEntity<>(categoryCreResDTO, HttpStatus.CREATED);
    }
}
