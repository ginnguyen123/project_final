package cg.api;

import cg.dto.cartDetail.*;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.repository.CartDetailRepository;
import cg.repository.ProductImportRepository;
import cg.service.cartDetail.ICartDetailService;
import cg.service.product.IProductImportService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.RequestSizeAndColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart-details")
public class CartDetailAPI {



    @Autowired
    ICartDetailService cartDetailService;

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    ProductImportRepository productImportRepository;

    @Autowired
    IProductImportService productImportService;

    @Autowired
    IProductService productService;

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
            if (quantity==null){
                throw new ResourceNotFoundException("Not found");
            }
        return new ResponseEntity<>(quantity,HttpStatus.OK);
    }

    @GetMapping("/price/{idProduct}")
    public ResponseEntity<?> getPriceWithProduct(@PathVariable Long idProduct){
        BigDecimal price = cartDetailRepository.getPriceWithProduct(idProduct);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @GetMapping("/color/product/{id}")
    public ResponseEntity<?> getAllSizeByProduct(@PathVariable Long id){
        List<EColor> colors = productImportService.getAllColorByProductAndQuantity(id);
        List<String> strColors = colors.stream().map(EColor::getValue).collect(Collectors.toList());
        return new ResponseEntity<>(strColors, HttpStatus.OK);
    }

    @PostMapping("/product-imp-cart-detail")
    public ResponseEntity<?> getProductImp(@RequestBody CartDetailListReqDTO cartDetailListRes){

        Long idProductRes = cartDetailListRes.getIdProduct();
        EColor color = cartDetailListRes.getColor();
        ESize size = cartDetailListRes.getSize();
        Long idProduct = productImportRepository.findProductBySizeAndColor(idProductRes,
                 color.getValue(), size.getValue());

        if (idProduct == null ){ // trường hợp product không có sản phẩm có size có color phù hợp
            Long zeroQuantity = 0l;
            CartDetailUpResDTO productIsNull = new CartDetailUpResDTO();
            return new ResponseEntity<>(productIsNull, HttpStatus.OK);
        }

        Optional<Product> productOp = productService.findById(idProduct); // lấy được id Product + title
        Long quantity = productImportRepository.checkQuantityProductImportBySizeAndColor(idProductRes,
                color.getValue(),size.getValue()); // lấy được số lượng sản phẩm còn trong kho
        Long quantityProduct = 0l;
        CartDetailUpResDTO cartDetailUpRes = new CartDetailUpResDTO(cartDetailListRes.getId(),
                idProductRes, quantity,size,  color,quantityProduct,productOp.get().getTitle(), productOp.get().getPrice());


        return new ResponseEntity<>(cartDetailUpRes, HttpStatus.OK);
    }
}
