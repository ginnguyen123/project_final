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

    @GetMapping()
    public ResponseEntity<?> getAllCategory(){
        List<Category> categoryParents = categoryService.findAllByCategoryParentIsNull(); // call category cha
        //Chuyển về list Category DTO cha với list Category DTO con = null
        List<CategogyParentDTO> categogyParentDTOS = categoryParents.stream().map(i -> i.toCategogyParentDTO()).collect(Collectors.toList());

        for (int i = 0; i<categogyParentDTOS.size(); i++){
//            khởi tạo 1 list con DTO mới sau mỗi idex để lưu vào category dto cha
            List<CategoryChildDTO> categoryChildDTOS = new ArrayList<>();
//            lấy ra danh sách con theo cha
            List<Category> categoryChilds = categoryService.findCategoriesByCategoryParentIdAndDeletedIsFalse(categoryParents.get(i));
            categoryChildDTOS.addAll(categoryChilds.stream().map(c -> c.toCategoryChild()).collect(Collectors.toList()));
            categogyParentDTOS.get(i).setCategoryChilds(categoryChildDTOS);
        }

        return new ResponseEntity<>(categogyParentDTOS, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories(){
        List<Category> categoryList = categoryService.findAllByDeletedIsFalse();
        List<CategoryChildDTO> categoryDTOS = categoryList.stream().map(i-> i.toCategoryChild()).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOS,HttpStatus.OK);
    }

    @PostMapping ("/get/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        if (categoryId == null){
            throw new DataInputException("Category does not exist");
        }
        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Category category = optionalCategory.get();

        return new ResponseEntity<>(category.toCategoryDTO(), HttpStatus.OK);
    }

    @GetMapping("/category-parents")
    public ResponseEntity<?> getAllCategoryParents(){
        List<Category> categories = categoryService.findAllByCategoryParentIsNull();
        List<CategoryChildDTO> categorys = categories.stream().map(i->i.toCategoryChild()).collect(Collectors.toList());
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }

    @GetMapping("/status={status}")
    public ResponseEntity<?> getAllCategoriesByStatus(@PathVariable String status) {
        List<Category> categoryList = categoryService.findAllCategoryByStatus(ECategoryStatus.valueOf(status.toUpperCase()));
        List<CategoryChildDTO> categoryDTOS = new ArrayList<>();

        if (categoryList.size() != 0){
            categoryDTOS = categoryList.stream().map(i -> i.toCategoryChild()).collect(Collectors.toList());
        }
        return new ResponseEntity<>(categoryDTOS,HttpStatus.OK);
    }



    @GetMapping("/{categoryParentId}")
    public ResponseEntity<?> getAllCategoryParentById(@PathVariable Long categoryParentId){
        List<Category> categories = categoryService.findAllByCategoryParent_Id(categoryParentId);
        List<CategoryChildDTO> categoryDTOS;

        if (categories.size() == 0){
            categoryDTOS = null;
        }

        else {
            categoryDTOS = categories.stream().map(i->i.toCategoryChild()).collect(Collectors.toList());
        }
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
