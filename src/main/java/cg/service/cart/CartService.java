package cg.service.cart;

import cg.dto.cart.*;
import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.EProductStatus;
import cg.model.enums.ESize;
import cg.model.location_region.LocationRegion;
import cg.model.product.Product;
import cg.model.product.ProductImport;
import cg.repository.*;
import cg.service.cart.response.CartListResponse;
import cg.service.products.IProductService;
import cg.utils.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class CartService implements ICartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    CartFilterRepository cartFilterRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LocationRegionRepository locationRegionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    IProductService productService;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Cart cart) {
        cart.setDeleted(true);
        cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Page<CartDTO> findAllByFilters(CartRequest keyword, Pageable pageable) {
        return cartFilterRepository.findAllByFilters(keyword, pageable).map(Cart::toCartDTO);
    }


    @Override
    public Page<CartListResponse> pageableByKeyword(CartRequest keyword, Pageable pageable) {

        if (keyword.getKeyword() != null) {
            keyword.setKeyword("%" + keyword.getKeyword() + "%");
        }

        return cartRepository.pageableByKeyword(keyword, pageable).map(CartListResponse::new);
    }

    @Override
    public void create(CartCreReqDTO cartCreReqDTO) {
        String email = cartCreReqDTO.getEmail();
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
        Customer customer = new Customer();
        LocationRegion locationRegion = cartCreReqDTO.getLocationRegion().toLocationRegion(customer);
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
            //set lai dia chi cũ của cus
            Long idLocationRegion = cartCreReqDTO.getLocationRegion().getId();
            Optional<LocationRegion> optionalLocationRegion = locationRegionRepository.findById(idLocationRegion);
            if (optionalLocationRegion.isPresent()) {
                List<LocationRegion> locationRegionsOfCustomer = customer.getLocationRegions();
                LocationRegion lcReve = locationRegionsOfCustomer.stream().
                        filter(i -> i.getId() == idLocationRegion).collect(Collectors.toList()).get(0);
                locationRegion = lcReve;
            }
        } else if (customerOptional.isEmpty()) {
            customer.setEmail(cartCreReqDTO.getEmail());
            customerRepository.save(customer);
            locationRegion.setId(null);
            locationRegionRepository.save(locationRegion);
        }
        Cart cart = new Cart();
        cart.setName_receiver(cartCreReqDTO.getReceivedName());
        cart.setPhone_receiver(cartCreReqDTO.getReceivedPhone());
        cart.setStatus(ECartStatus.getECartStatus(cartCreReqDTO.getStatus()));

        List<CartDetailCreReqDTO> cartDetailCreReqDTOs = cartCreReqDTO.getCartDetailDTOList();
        BigDecimal totalTotal = BigDecimal.ZERO;
        List<CartDetail> cartDetails = new ArrayList<>();
        for (CartDetailCreReqDTO item : cartDetailCreReqDTOs) {
            CartDetail cartDetail = item.toCartDetail();
            Optional<Product> product = productRepository.findById(item.getProductId());
            BigDecimal price = product.get().getPrice();
            long quantity = item.getQuantity();
            BigDecimal totalLe = price.multiply(BigDecimal.valueOf(quantity));
            cartDetail.setTotalAmount(totalLe);
            totalTotal = totalTotal.add(totalLe);
            cartDetail.setProduct(product.get());
            cartDetail.setCart(cart);
            cartDetails.add(cartDetail);
        }

        cart.setTotalAmount(totalTotal);
        cart.setLocationRegion(locationRegion);
        cart.setCustomer(customer);
        cartRepository.save(cart);
        cartDetailRepository.saveAll(cartDetails);

    }

    @Override
    public CartDetail createNewCartDetail(CartCreMiniCartReqDTO cartCreMiniCartReqDTO, Cart cart) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setQuantity(cartCreMiniCartReqDTO.getQuantity());
        Optional<Product> productOptional = productService.findById(cartCreMiniCartReqDTO.getProductId());

        if (!productOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }
        Product product  = productOptional.get();
        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(cartCreMiniCartReqDTO.getQuantity()));
        cartDetail.setTotalAmount(totalAmount);
        cartDetail.setSize(ESize.getESize(cartCreMiniCartReqDTO.getSize()));
        cartDetail.setColor(EColor.getEColor(cartCreMiniCartReqDTO.getColor()));
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        return cartDetail;
    }

    @Override
    public Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id) {
        return cartRepository.getCartDTOByIdDeletedIsFalse(id);
    }

    @Override
    public CartListResponse update(CartUpReqDTO cartUpReqDTO) {
        Optional<Cart> optionalCart = cartRepository.findById(cartUpReqDTO.getId());
        if (!optionalCart.isPresent()) {
            throw new ResourceNotFoundException("Not found cart ");
        }
        Cart cart = optionalCart.get();
        cart.setName_receiver(cartUpReqDTO.getName_receiver());
        cart.setPhone_receiver(cartUpReqDTO.getPhone_receiver());
        List<CartDetailUpReqDTO> cartDetailDTOList = cartUpReqDTO.getCartDetailDTOList(); //cartDetailUp đang null
        BigDecimal totalTotal = BigDecimal.ZERO;
        List<CartDetail> cartDetails = new ArrayList<>();
        for (CartDetailUpReqDTO item : cartDetailDTOList) {
            CartDetail cartDetail = item.toCartDetail();
            Optional<Product> product = productRepository.findById(item.getProductId());
            BigDecimal price = product.get().getPrice();
            long quantity = item.getQuantity();
            BigDecimal totalLe = price.multiply(BigDecimal.valueOf(quantity));
            cartDetail.setTotalAmount(totalLe);
            totalTotal = totalTotal.add(totalLe);
            cartDetail.setProduct(product.get());
            cartDetail.setCart(cart);
            cartDetails.add(cartDetail);
        }
        List<CartDetail> cartDetailList = cartDetailRepository.saveAll(cartDetails);
        cart.setTotalAmount(totalTotal);
        Optional<LocationRegion> locationRegion = locationRegionRepository.findById(cartUpReqDTO.getLocationRegion().getId());
        cart.setLocationRegion(locationRegion.get());
        cart.setCartDetails(cartDetailList);
        cartRepository.save(cart);
        CartListResponse cartUpResDTO = new CartListResponse(cart);
        return cartUpResDTO;
        // set lại các field người nhận.
        // remove cartdetail
        // add cartdetail.
        // update cart.
    }

    @Override
    public Cart findCartsByCustomerIdAndStatusIsCart(Long customerId, ECartStatus status) {
        return cartRepository.findCartsByCustomerIdAndStatusIsCart(customerId,status);
    }
}
