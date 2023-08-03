package cg.api;

import cg.dto.cart.*;

import cg.dto.cartDetail.CartDetailResDTO;
import cg.dto.customerDTO.CustomerDTO;
import cg.dto.locationRegionDTO.LocationRegionDTO;
import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.JwtResponse;
import cg.model.bill.Bill;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.customer.Customer;
import cg.model.discount.Discount;
import cg.model.enums.ECartStatus;


import cg.model.customer.Customer;

import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.location_region.LocationRegion;


import cg.model.customer.Customer;
import cg.model.location_region.LocationRegion;

import cg.model.product.Product;
import cg.model.user.User;
import cg.repository.CartRepository;
import cg.repository.LocationRegionRepository;
import cg.service.ExistService;

import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.service.bill.IBillService;
import cg.service.cart.ICartService;
import cg.service.cart.response.CartListResponse;
import cg.service.cartDetail.ICartDetailService;
import cg.service.customer.ICustomerService;
import cg.service.locationRegion.ILocationRegionService;
import cg.service.products.IProductService;
import cg.service.user.IUserService;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import cg.utils.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    IBillService billService;

    @Autowired
    IUserService userService;

    @Autowired
    IProductService productService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerService customerService;
    private ExistService existService;

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private ILocationRegionService locationRegionService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @GetMapping
    public ResponseEntity<?> getAllDeleteFalse() {
        List<Cart> carts = cartService.findAll();
        List<CartDTO> cartDTOS = carts.stream().map(Cart::toCartDTO).collect(Collectors.toList());
        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @GetMapping("/cart-details/{customerId}")
    public ResponseEntity<?> getAllCartDetails(@PathVariable Long customerId) {
        ECartStatus eCartStatus =  ECartStatus.getECartStatus("ISCART");
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
        List<CartDetail> cartDetailList = cartDetailService.findCartDetailsByCartAndDeletedIsFalse(cart);
        //Da tinh totalAmount CartDetail
        List<CartDetailResDTO> cartDetailResDTOS = cartDetailList.stream().map(item->item.toCartDetailResDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(cartDetailResDTOS,HttpStatus.OK);
    }

    @GetMapping("/amount/{customerId}")
    public ResponseEntity<?> getTotalAmountCart(@PathVariable Long customerId) {
        ECartStatus eCartStatus =  ECartStatus.getECartStatus("ISCART");
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
        CartResDTO cartResDTO = cart.toCartResDTO();
        return new ResponseEntity<>(cartResDTO, HttpStatus.OK);
    }

    @PatchMapping("/cart-details/{customerId}/{cartDetailId}")
    public ResponseEntity<?> increaseQuantityCartDetail(@PathVariable Long customerId,@PathVariable Long cartDetailId, @RequestBody Long quantity) {
        ECartStatus eCartStatus =  ECartStatus.getECartStatus("ISCART");
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
        CartDetail cartDetail = cartDetailService.findById(cartDetailId).get();
        Product product = cartDetail.getProduct();
        cartDetail.setQuantity(quantity);
        BigDecimal totalAmountCartDetail = cartDetailService.getTotalAmountCartDetail(product, quantity);
        cartDetail.setTotalAmount(totalAmountCartDetail);
        cartDetailService.save(cartDetail);
        BigDecimal totalAmountCart = cartService.getTotalAmountCart(cart);
        cart.setTotalAmount(totalAmountCart);
        cartService.save(cart);
//        Cart newCart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
//        CartResDTO cartResDTO = newCart.toCartResDTO();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cart-details/{customerId}/{cartDetailId}")
    public ResponseEntity<?> removeCartDetail(@PathVariable Long customerId, @PathVariable Long cartDetailId) {
        ECartStatus eCartStatus =  ECartStatus.getECartStatus("ISCART");
        Cart cart = cartService.findCartsByCustomerIdAndStatusIsCart(customerId, eCartStatus);
        CartDetail cartDetail = cartDetailService.findById(cartDetailId).get();
        cartDetail.setDeleted(true);
        cartDetailService.save(cartDetail);
        BigDecimal totalAmountCart = cartService.getTotalAmountCart(cart);
        cart.setTotalAmount(totalAmountCart);
        cartService.save(cart);
        CartResDTO cartDTO = cart.toCartResDTO();
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
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

        Cart cart =  cartService.create(cartCreReqDTO);
        CartCreResDTO cartCreResDTO = cart.toCartCreResDTO();
        return new ResponseEntity<>(cartCreResDTO,HttpStatus.OK);
    }

    @PostMapping("/add")
    public  ResponseEntity<?> addToCart(@RequestBody CartCreMiniCartReqDTO cartCreMiniCartReqDTO) {
        //check đã đăng nhập chưa nếu chưa thì giữ nguyên nếu có set id bằng current i
        String currentUserName = cartCreMiniCartReqDTO.getUsername();
        Long idProduct = cartCreMiniCartReqDTO.getProductId();
        Optional<Product> produtOp = productService.findById(idProduct);
        if (!produtOp.isPresent())
            throw new DataInputException(AppConstant.ENTITY_NOT_EXIT_ERROR);

        Product product = produtOp.get();
        Discount discount = produtOp.get().getDiscount();
        ESize eSize = ESize.valueOf(cartCreMiniCartReqDTO.getSize());
        EColor eColor = EColor.valueOf(cartCreMiniCartReqDTO.getColor());
        Long quantity = cartCreMiniCartReqDTO.getQuantity();
        boolean isChangeProduct = false;

        if (currentUserName != null && currentUserName.equals("")){
            Optional<User> currentUserOp = userService.findByUsername(currentUserName);
            if (currentUserOp.isPresent()){
                User currentUser = currentUserOp.get();
                Optional<Cart> cartOp = cartService.findCartsByUserAndStatusIsCart(currentUser, ECartStatus.ISCART);
                if (cartOp.isPresent()){
                    Cart currentCart = cartOp.get();
                    List<CartDetail> cartDetails = currentCart.getCartDetails();
                    for (CartDetail cartDetail : cartDetails){
                        if (cartDetail.getProduct().getId() == idProduct
                                && cartDetail.getSize().getValue().equals(eSize.getValue())
                                && cartDetail.getColor().getValue().equals(eColor.getValue())){
                            Long newQuantity =  cartDetail.getQuantity() + quantity;
                            cartDetail.setQuantity(newQuantity);
                            BigDecimal newTotalAmount = BigDecimal.ZERO;
                            if (discount != null){
                                newTotalAmount = BigDecimal.valueOf(newQuantity).multiply(
                                        (product.getPrice().multiply(BigDecimal.valueOf(discount.getDiscount())).divide(BigDecimal.valueOf(100L))));
                                cartDetail.setTotalAmount(newTotalAmount);
                            }else {
                                newTotalAmount = BigDecimal.valueOf(newQuantity).multiply(product.getPrice());
                                cartDetail.setTotalAmount(newTotalAmount);
                            }
                            isChangeProduct = true;
                        }
                    }

                    if (!isChangeProduct){
                        CartDetail newCartDetail = new CartDetail();
                        newCartDetail.setId(null);
                        BigDecimal newTotalAmount = BigDecimal.ZERO;
                        if (discount != null){
                            newTotalAmount = BigDecimal.valueOf(quantity).multiply(
                                    (product.getPrice().multiply(BigDecimal.valueOf(discount.getDiscount())).divide(BigDecimal.valueOf(100L))));
                            newCartDetail.setTotalAmount(newTotalAmount);
                        }else {
                            newTotalAmount = BigDecimal.valueOf(quantity).multiply(product.getPrice());
                            newCartDetail.setTotalAmount(newTotalAmount);
                        }
                        newCartDetail.setCart(currentCart);
                        newCartDetail.setProduct(product);
                        newCartDetail.setQuantity(quantity);
                        newCartDetail.setColor(eColor);
                        newCartDetail.setSize(eSize);
                        cartDetails.add(newCartDetail);
                    }

                    cartDetails = cartDetailService.saveAll(cartDetails);
                    BigDecimal totalAmoutCart = cartDetailService.totalAmoutByCart(currentCart);
                    currentCart.setTotalAmount(totalAmoutCart);
                    cartService.save(currentCart);
                    return new ResponseEntity<>(new SizeCartResDTO(currentCart.getId(), cartDetails.size()),HttpStatus.OK);
                }else {
                    Cart cart = new Cart();
                    cart.setId(null);
                    cart.setUser(currentUser);
                    cart.setStatus(ECartStatus.ISCART);
                    cart = cartService.save(cart);
                    List<CartDetail> cartDetails = new ArrayList<>();
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setId(null);
                    BigDecimal newTotalAmount = BigDecimal.ZERO;
                    if (discount != null){
                        newTotalAmount = BigDecimal.valueOf(quantity).multiply(
                                (product.getPrice().multiply(BigDecimal.valueOf(discount.getDiscount())).divide(BigDecimal.valueOf(100L))));
                        newCartDetail.setTotalAmount(newTotalAmount);
                    }else {
                        newTotalAmount = BigDecimal.valueOf(quantity).multiply(product.getPrice());
                        newCartDetail.setTotalAmount(newTotalAmount);
                    }
                    newCartDetail.setCart(cart);
                    newCartDetail.setProduct(product);
                    newCartDetail.setQuantity(quantity);
                    newCartDetail.setColor(eColor);
                    newCartDetail.setSize(eSize);
                    cartDetails.add(newCartDetail);
                    cartDetailService.saveAll(cartDetails);
                    BigDecimal totalAmoutCart = cartDetailService.totalAmoutByCart(cart);
                    cart.setTotalAmount(totalAmoutCart);
                    cartService.save(cart);
                    return new ResponseEntity<>(new SizeCartResDTO(cart.getId(), cartDetails.size()),HttpStatus.OK);
                }
            }
            else {
                throw new DataInputException(AppConstant.ENTITY_NOT_EXIT_ERROR);
            }
        }

        return new ResponseEntity<>(currentUserName,HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long id, @Validated @RequestBody CartUpReqDTO cartUpReqDTO, BindingResult bindingResult ) throws IOException {
        new CartUpReqDTO().validate(cartUpReqDTO,bindingResult);
        System.out.println(id);
        cartUpReqDTO.setId(id);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        CartUpResDTO cartUpResDTO = cartService.update(cartUpReqDTO);
        return new ResponseEntity<>(cartUpResDTO,HttpStatus.OK);
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusCart( @RequestBody CartListResponse cartListResponse) {
        Optional<Cart> cartOptional = cartService.findById(cartListResponse.getId());
        if (!cartOptional.isPresent()) {
            throw new DataInputException("cart is not found");
        }
        Cart cart = cartOptional.get();
        if (cartListResponse.getStatus().getValue().equals("PAID")) {
            cart.setStatus(ECartStatus.UNPAID);
        } else {
            cart.setStatus(ECartStatus.PAID);
        }

        cartService.save(cart);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerCheckout(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new DataInputException("customer is not found");
        }
            Customer customer = customerOptional.get();
        List<LocationRegion> locationRegions = locationRegionService.findAllByCustomer(customer);

        List<LocationRegionDTO> locationRegionDTOS = locationRegions.stream().map(LocationRegion::toLocationRegionDTO).collect(Collectors.toList());

        CustomerDTO customerDTO = customer.toCustomerDTO(locationRegionDTOS);
        return new ResponseEntity<>(customerDTO,HttpStatus.OK);
    }

    @PatchMapping("/checkout")
    public ResponseEntity<?> createCheckoutCart (@Valid @RequestBody CartCheckoutDTO cartCheckoutDTO) {
        Optional<Cart> cartOptional = cartRepository.findById(cartCheckoutDTO.getCartId());
        Cart cart = cartOptional.get();
        cart.setStatus(ECartStatus.UNPAID);
        cart.setPhone_receiver(cartCheckoutDTO.getPhone_receiver());
        cart.setName_receiver(cartCheckoutDTO.getName_receiver());
        LocationRegion locationRegion = cartCheckoutDTO.getLocationRegion().toLocationRegion(cart.getCustomer());
        locationRegionRepository.save(locationRegion);
        //LocationRegion savedLocationRegion = locationRegionService.findLocationRegionByAddress(cartCheckoutDTO.getLocationRegion().getAddress());
        cart.setLocationRegion(locationRegion);
        cartRepository.saveAndFlush(cart);
        Bill bill = new Bill();
        bill.setPhone_receiver(cart.getPhone_receiver());
        bill.setName_receiver(cart.getName_receiver());
        bill.setLocationRegion(locationRegion);
        bill.setTotalAmount(cart.getTotalAmount());
        Customer customer = cart.getCustomer();
        User user = userService.findUserByCustomer(customer);
        bill.setUser(user);
        bill.setCustomer(customer);
        bill.setStatus(cart.getStatus());
        billService.save(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
