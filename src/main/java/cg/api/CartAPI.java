package cg.api;

import cg.dto.cart.*;
import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.cartDetail.CartDetailResDTO;
import cg.dto.productImport.*;
import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.service.cart.ICartDetailService;
import cg.service.cart.ICartService;
import cg.service.cart.response.CartListResponse;
import cg.service.customer.ICustomerService;
import cg.service.products.IProductService;
import cg.utils.AppUtils;
import cg.utils.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartAPI {
    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllDeleteFalse() {
        List<Cart> carts = cartService.findAll();
        List<CartDTO> cartDTOS = carts.stream().map(Cart::toCartDTO).collect(Collectors.toList());
        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @GetMapping("/cart-details/{customerId}")
    public ResponseEntity<?> getAllCartDetails(@PathVariable Long customerId) {
//        List<CartDetail> cartDetailList = cartDetailService.
        ECartStatus eCartStatus =  ECartStatus.getECartStatus("ISCART");
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
        List<CartDetail> cartDetailList = cart.getCartDetails();
        List<CartDetailResDTO> cartDetailResDTOS = cartDetailList.stream().map(item->item.toCartDetailResDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(cartDetailResDTOS,HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> pageableByKeyword(@RequestBody CartRequest keyword ,Pageable pageable) {
        Page<CartListResponse> carts = cartService.pageableByKeyword(keyword,pageable);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createCart(@RequestBody @Validated CartCreReqDTO cartCreReqDTO ,  BindingResult bindingResult){
        new CartCreReqDTO().validate(cartCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

          cartService.create(cartCreReqDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public  ResponseEntity<?> addToCart(@RequestBody CartCreMiniCartReqDTO cartCreMiniCartReqDTO) {
        if (cartCreMiniCartReqDTO.getCustomerId() == null) {
            throw new DataInputException("id customer null");
        }
        Long customerId = cartCreMiniCartReqDTO.getCustomerId();
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer is not found");
        }
        Customer customer = customerOptional.get();
        String status_str = cartCreMiniCartReqDTO.getStatus();
        ECartStatus status = ECartStatus.getECartStatus(status_str);
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customer.getId(), status);
        Boolean check = false;
        int cartDetail_size;
        if (cart != null) {
            List<CartDetail> cartDetailList = cart.getCartDetails();
            CartDetail cartDetail = new CartDetail();
            for (CartDetail item : cartDetailList) {
                if (item.getProduct().getId()==cartCreMiniCartReqDTO.getProductId() && item.getSize().getValue().equals(cartCreMiniCartReqDTO.getSize()) && item.getColor().getValue().equals(cartCreMiniCartReqDTO.getColor())) {
                    Long current_quantity = item.getQuantity();
                    Long new_quantity = cartCreMiniCartReqDTO.getQuantity() + current_quantity;
                    item.setQuantity(new_quantity);
                    Optional<Product> productOptional = productService.findById(cartCreMiniCartReqDTO.getProductId());
                    if (!productOptional.isPresent()) {
                        throw new DataInputException("Product is not found");
                    }
                    Product product = productOptional.get();
                    BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(new_quantity));
                    item.setTotalAmount(totalAmount);
                    cartDetailService.save(item);
                    check = true;
                    break;
                } else {
                    cartDetail = cartService.createNewCartDetail(cartCreMiniCartReqDTO, cart);

                }
            }
            if (!check) {
                cartDetailList.add(cartDetail);
                cartDetailService.save(cartDetail);
            }

            BigDecimal total = BigDecimal.ZERO;
            for (CartDetail item : cartDetailList) {
                total = total.add(item.getTotalAmount());
            }
            cart.setTotalAmount(total);

            cartService.save(cart);
            cartDetail_size = cartDetailList.size();
        } else {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setStatus(ECartStatus.getECartStatus(cartCreMiniCartReqDTO.getStatus()));
            CartDetail cartDetail = cartService.createNewCartDetail(cartCreMiniCartReqDTO, newCart);
            List<CartDetail> cartDetailList = new ArrayList<>();
            cartDetailList.add(cartDetail);
            newCart.setCartDetails(cartDetailList);
            newCart.setTotalAmount(cartDetail.getTotalAmount());
            cartService.save(newCart);
            cartDetailService.save(cartDetail);
            cartDetail_size = cartDetailList.size();
        }

        return new ResponseEntity<>(cartDetail_size,HttpStatus.OK);

    }



    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long id, @Validated @RequestBody CartUpReqDTO cartUpReqDTO, BindingResult bindingResult ) throws IOException {
        new CartUpReqDTO().validate(cartUpReqDTO,bindingResult);

        cartUpReqDTO.setId(id);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        CartListResponse cartUpResDTO = cartService.update(cartUpReqDTO);
        return new ResponseEntity<>(cartUpResDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Cart cart = cartService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found this cart to delete"));
        cartService.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        CartDTO cartDTO = cartService
                .getCartDTOByIdDeletedIsFalse(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Not found this cart"));
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }



}
