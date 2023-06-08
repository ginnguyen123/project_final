package cg.api;

import cg.dto.brand.BrandCreResDTO;
import cg.dto.brand.BrandDTO;
import cg.exception.DataInputException;
import cg.exception.FieldExistsException;
import cg.model.brand.Brand;
import cg.repository.BrandRepository;
import cg.service.brand.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/brands")
public class BrandAPI {

    @Autowired
    private IBrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Brand> brands = brandService.findAll();
        List<BrandDTO> brandDTOS = brands.stream().map(item -> item.toBrandDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(brandDTOS,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllBrandsNotDelete() {
        List<Brand> brands = brandService.findAllByDeletedFalse();
        List<BrandDTO> brandDTOS = brands.stream().map(item -> item.toBrandDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(brandDTOS, HttpStatus.OK);
    }


    @PatchMapping("/update/{brandId}")
    public ResponseEntity<?> update(@PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
        Optional<Brand> brandOptional = brandService.findById(brandId);
        if (!brandOptional.isPresent()) {
            throw new DataInputException("Brand is not found");
        }
        Brand updatedBrand = brandOptional.get();
        if (brandService.existsBrandByName(brandDTO.getName())) {
            throw new DataInputException("Name brand is existed");
        }
        updatedBrand.setName(brandDTO.getName());
        brandService.save(updatedBrand);
        BrandDTO updatedBrandDTO = updatedBrand.toBrandDTO();
        return new ResponseEntity<>(updatedBrandDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{brandID}")
    public ResponseEntity<?> delete (@PathVariable Long brandID) {
        Optional<Brand> brandOptional = brandService.findById(brandID);
        if (!brandOptional.isPresent()) {
            throw new DataInputException("Brand is not found");
        }
        Brand brand = brandOptional.get();
        brandService.delete(brand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody String name){

        if (name.isEmpty() || name.equals(null)){
            throw new DataInputException("The role is required");
        }

        if (brandService.existsBrandByName(name)){
            throw new FieldExistsException("Brand's name already exists!");
        }

        String nameUpCase = name.toUpperCase();
        Brand brand = new Brand(null, nameUpCase);
        brandService.save(brand);
        BrandCreResDTO brandDTO = brand.toBrandCreResDTO();
        return new ResponseEntity<>(brandDTO,HttpStatus.CREATED);
    }

}
