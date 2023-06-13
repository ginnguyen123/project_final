package cg.api;

import cg.dto.category.CategoryCreReqDTO;
import cg.dto.category.CategoryCreResDTO;
import cg.dto.category.CategoryDTO;
import cg.exception.DataInputException;
import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.service.category.ICategoryService;
import cg.service.products.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories(){
        List<Category> categoryList = categoryService.findAll();
        List<CategoryCreResDTO> categoryCreResDTOList = new ArrayList<>();
        for (Category item: categoryList) {
            Optional<Category> categoryOptional = categoryService.findById(item.getId());
            if (!categoryOptional.isPresent()) {
                throw new DataInputException("Category Parent is not found");
            }
            Category category = categoryOptional.get();
            categoryCreResDTOList.add(category.toCategoryCreResDTO());
        }
        return new ResponseEntity<>(categoryCreResDTOList,HttpStatus.OK);
    }



    @GetMapping("/status={status}")
    public ResponseEntity<?> getAllCategoriesByStatus(@PathVariable String status) {
        List<Category> categoryList = categoryService.findAllCategoryByStatus(ECategoryStatus.valueOf(status.toUpperCase()));
        List<CategoryCreResDTO> categoryCreResDTOS = new ArrayList<>();
        for (Category item: categoryList) {
            Optional<Category> categoryOptional = categoryService.findById(item.getId());
            if (!categoryOptional.isPresent()) {
                throw new DataInputException("Category Parent is not found");
            }
            Category category = categoryOptional.get();
            categoryCreResDTOS.add(category.toCategoryCreResDTO());
        }
        return new ResponseEntity<>(categoryCreResDTOS, HttpStatus.OK);
    }

    @PostMapping("/{idParent}")
    public ResponseEntity<?> getAllCategoriesByStatus(@PathVariable Long idParent){
        Optional<Category> categoryOptional = categoryService.findById(idParent);
        if (!categoryOptional.isPresent()){
            throw new DataInputException("khong ton tai");
        }

        String name = categoryOptional.get().getName();
        List<String> aolist = new ArrayList<>();
        if (idParent == 1){

        }

        else {

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


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
