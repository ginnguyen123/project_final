package cg.api;

import cg.dto.category.*;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
        List<Category> categoryList = categoryService.findCategoriesByCategoryParentNotNull();
        Map<Category, List<Category>> map = categoryList.stream()
                .collect(groupingBy(Category::getCategoryParent));
        List<CategoryCreResDTO> categoryCreResDTOList = new ArrayList<>();
//        Phương thức groupingBy được sử dụng để nhóm các đối tượng Category theo giá trị được trả về
//        từ phương thức getCategoryParent của mỗi đối tượng. Kết quả của groupingBy là một Map với khóa
//        là giá trị được trả về từ getCategoryParent và giá trị là một danh sách các đối tượng Category có
//        cùng giá trị getCategoryParent.
        for (Map.Entry<Category, List<Category>> entry : map.entrySet()) {
            CategoryCreResDTO categoryCreResDTO = new CategoryCreResDTO();
            categoryCreResDTO.setId(entry.getKey().getId());
            categoryCreResDTO.setName(entry.getKey().getName());
            categoryCreResDTO.setStatus(entry.getKey().getStatus());
            categoryCreResDTO.setCategoryDTOS(entry.getValue().stream().map(item -> item.toCategoryDTO()).collect(Collectors.toList()));
            categoryCreResDTOList.add(categoryCreResDTO);
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

        if (categoryCreReqDTO.getCategoryParentId() == null){
            newCategory.setCategoryParent(null);
        }
        else {
            if (!categoryService.existsById(categoryCreReqDTO.getCategoryParentId())){
                newCategory.setCategoryParent(null);
            }else {
                Optional<Category> categoryId = categoryService.findById(categoryCreReqDTO.getCategoryParentId());
                Category categoryParentById = categoryId.get();
                newCategory.setCategoryParent(categoryParentById);
            }
        }

        ECategoryStatus status = categoryCreReqDTO.getStatus();
        if (status == ECategoryStatus.SUMMER || status == ECategoryStatus.WINTER){
            newCategory.setStatus(status);
        }else {
            newCategory.setStatus(null);
        }
        categoryService.save(newCategory);

        CategoryCreateRespDTO createResp = newCategory.toCategoryCreateRespDTO();

        return new ResponseEntity<>(createResp, HttpStatus.CREATED);
    }
}
