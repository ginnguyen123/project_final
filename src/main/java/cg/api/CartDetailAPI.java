package cg.api;

import cg.dto.cart.CartDTO;
import cg.dto.cart.CartUpReqDTO;
import cg.dto.cart.CartUpResDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailNotCart;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.CartDetail;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.repository.ProductImportRepository;
import cg.service.cartDetail.ICartDetailService;
import cg.service.product.IProductImportService;
import cg.utils.AppUtils;
import cg.utils.RequestSizeAndColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart-details")
public class CartDetailAPI {

    @Autowired
    ICartDetailService cartDetailService;
    @Autowired
    ProductImportRepository productImportRepository;
    @Autowired
    IProductImportService productImportService;
    @Autowired
    AppUtils appUtils;


    @GetMapping("/{id}")
    public ResponseEntity<?> getCartDetailByIdCart(@PathVariable Long id) {
       List<CartDetail> cartDetail = cartDetailService.findAllByCart_IdAndDeletedIsFalse(id);
        return new ResponseEntity<>(cartDetail.stream().map(CartDetail::toCartDetailNotCart).collect(Collectors.toList()), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCartDetail(@PathVariable Long id, @Validated @RequestBody CartDetailUpReqDTO cartDetailUpReqDTO, BindingResult bindingResult ) throws IOException {
        new CartDetailUpReqDTO().validate(cartDetailUpReqDTO,bindingResult);

        cartDetailUpReqDTO.setId(id);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
         CartDetailNotCart cartDetailNotCart = cartDetailService.update(cartDetailUpReqDTO);
        return new ResponseEntity<>(cartDetailNotCart,HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        CartDetail cartDetail = cartDetailService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found this cartDetail to delete"));
        cartDetailService.delete(cartDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find-id-cart-detail/{id}")
    public ResponseEntity<?> getCartDetailById(@PathVariable Long id) {
        CartDetailDTO cartDetail = cartDetailService.getByIdAndDeletedIsFalse(id).orElseThrow(
                ()-> new ResourceNotFoundException("Not found cartDetail")
        );
        return new ResponseEntity<>(cartDetail, HttpStatus.OK);
    }

    @GetMapping("/sizes")
    public ResponseEntity<?> getAllESize() {
        return new ResponseEntity<>(ESize.getEnumValues(), HttpStatus.OK);
    }

    @GetMapping("/colors")
    public ResponseEntity<?> getAllEColor() {
        return new ResponseEntity<>(EColor.getEnumValues(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?>getCartDetailWithProduct(@PathVariable Long id){
        Long quantity = productImportRepository.checkQuantityProductImport(id);
        return new ResponseEntity<>(quantity,HttpStatus.OK);
    }
    @PostMapping("/size_color")
    public ResponseEntity<?>getCartDetailWithSizeAndColor(@RequestBody RequestSizeAndColor requestSizeAndColor){
        String size = requestSizeAndColor.getSize();
        String  color = requestSizeAndColor.getColor();
        Long id = requestSizeAndColor.getId();
        Long quantity = productImportRepository.checkQuantityProductImportBySizeAndColor(id , color , size);
        System.out.print(quantity);
        return new ResponseEntity<>(quantity,HttpStatus.OK);
    }



    @GetMapping("/color/product/{id}")
    public ResponseEntity<?> getAllSizeByProduct(@PathVariable Long id){
        List<EColor> colors = productImportService.getAllColorByProductAndQuantity(id);
        List<String> strColors = colors.stream().map(i -> i.getValue()).collect(Collectors.toList());
        return new ResponseEntity<>(strColors, HttpStatus.OK);
    }


}
