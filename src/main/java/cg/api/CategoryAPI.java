package cg.api;

import cg.dto.category.CategoryCreReqDTO;
import cg.dto.category.CategoryCreResDTO;
import cg.dto.category.CategoryDTO;
import cg.exception.DataInputException;
import cg.model.category.Category;
import cg.model.enums.ECategoryStatus;
import cg.model.media.Media;
import cg.repository.MediaRepository;
import cg.service.category.ICategoryService;
import cg.service.media.IUploadMediaService;
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

    @Autowired
    private IUploadMediaService uploadMediaService;

    @Autowired
    private MediaRepository mediaRepository;

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
    public ResponseEntity<?> create(CategoryCreReqDTO categoryCreReqDTO){
        Category newCategory = new Category();
        newCategory.setId(null);

        if (categoryCreReqDTO.getName().isEmpty() || categoryCreReqDTO.getName() == null){
            throw new DataInputException("The name's required!");
        }
        newCategory.setName(categoryCreReqDTO.getName());

        if (categoryCreReqDTO.getCategoryAvatar() == null){
            throw new DataInputException("The category's image required!");
        }
        Media media = new Media();
        mediaRepository.save(media);
        media = uploadMediaService.uploadImageAndSaveImage(categoryCreReqDTO.getCategoryAvatar(), media);
        newCategory.setCategoryAvatar(media);

        if (categoryCreReqDTO.getCategoryParentId() == null && categoryCreReqDTO.getCategoryParentName() == null){
            Category categoryParent = new Category();
            newCategory.setCategoryParent(categoryParent);
        }else {
            Optional<Category> categoryId = categoryService.findById(categoryCreReqDTO.getCategoryParentId());
            if (!categoryId.isPresent()){
                Optional<Category> categoryName = categoryService.findByName(categoryCreReqDTO.getName());
                if (!categoryName.isPresent()){
                    Category categoryParent = new Category();
                    newCategory.setCategoryParent(categoryParent);
                }else {
                    Category category = categoryName.get();
                    newCategory.setCategoryParent(category);
                }
            }else {
                Category category = categoryId.get();
                newCategory.setCategoryParent(category);
            }
        }

        String status = categoryCreReqDTO.getStatus();
        if (ECategoryStatus.getECategoryStatus(status) == null){
            newCategory.setStatus(null);
        }else {
            ECategoryStatus eCategoryStatus = ECategoryStatus.getECategoryStatus(status);
            newCategory.setStatus(eCategoryStatus);
        }

        Category categoryRes = categoryService.save(newCategory);

        CategoryDTO categoryCreResDTO = categoryRes.toCategoryDTO();

        return new ResponseEntity<>(categoryCreResDTO, HttpStatus.CREATED);
    }
}
