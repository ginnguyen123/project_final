package cg.api;

import cg.dto.cart.CartDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.service.cartDetail.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart-details")
public class CartDetailAPI {

    @Autowired
    ICartDetailService cartDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartDetailByIdCart(@PathVariable Long id) {
       List<CartDetail> cartDetail = cartDetailService.findAllByCart_IdAndAndDeletedIsFalse(id);
        return new ResponseEntity<>(cartDetail.stream().map(i->i.toCartDetailNotCart()).collect(Collectors.toList()), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        CartDetail cartDetail = cartDetailService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found this cartDetail to delete"));
        cartDetailService.delete(cartDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCartDetailById(@PathVariable Long id) {
//        CartDetailDTO cartDetailDTO = cartDetailService
//                .getByIdAndDeletedIsFalse(id)
//                .orElseThrow(
//                        ()-> new ResourceNotFoundException("Not found this cart"));
//        return new ResponseEntity<>(cartDetailDTO, HttpStatus.OK);
//    }
}
