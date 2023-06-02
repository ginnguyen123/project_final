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
