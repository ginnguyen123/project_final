package cg.api;

import cg.dto.category.CategoryCreReqDTO;
import cg.dto.category.CategoryCreResDTO;
import cg.dto.category.CategoryDTO;
import cg.exception.DataInputException;
import cg.model.category.Category;
import cg.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;

//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<Category> categoryList = categoryService.findAll();
//        List<CategoryDTO> categoryDTOS = categoryList.stream().map(item-> item.toCategoryDTO()).collect(Collectors.toList());
//        return new ResponseEntity<>(categoryDTOS,HttpStatus.OK);
//    }

//    @GetMapping()
//    public ResponseEntity<?> getAllCategoryShowing() {
//        List<Category> categoryList = categoryService.
//    }

    @GetMapping("/{categoryParentId}")
    public ResponseEntity<?> getAllCategoryParentById(@PathVariable Long categoryParentId){
        List<Category> categories = categoryService.findAllByCategoryParent_Id(categoryParentId);
        List<CategoryDTO> categoryDTOS = categories.stream().map(item -> item.toCategoryDTO()).collect(Collectors.toList());

        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryCreReqDTO categoryCreReqDTO){
        Optional<Category> categoryOptional = categoryService.findById(categoryCreReqDTO.getCategoryParentId());
        if (!categoryOptional.isPresent()){
            throw new DataInputException("Category is not exist!");
        }
        Category newCategory = new Category();

        if (categoryCreReqDTO.getCategoryParentId() == null){
            newCategory.getCategoryParent().setId(categoryCreReqDTO.getId());
            newCategory.getCategoryParent().setName(categoryCreReqDTO.getName());
        }

        Category categoryDb = categoryOptional.get();


        newCategory.setId(null);
        newCategory.setName(categoryCreReqDTO.getCategoryParentName());
        newCategory.setCategoryParent(categoryDb);

        Category categoryRes = categoryService.save(newCategory);

        CategoryCreResDTO categoryCreResDTO = categoryRes.toCategoryCreResDTO();

        return new ResponseEntity<>(categoryCreResDTO, HttpStatus.CREATED);
    }
}
