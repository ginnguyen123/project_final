package cg.service.cart;

import cg.dto.cart.*;
import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.cartDetail.CartDetailDTO;
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
        return cartFilterRepository.findAllByFilters(keyword,pageable).map(Cart::toCartDTO);
    }


    @Override
    public Page<CartListResponse> pageableByKeyword(CartRequest keyword, Pageable pageable) {

        if(keyword.getKeyword() != null){
            keyword.setKeyword("%"+keyword.getKeyword()+"%");
        }

        return cartRepository.pageableByKeyword(keyword,pageable).map(CartListResponse::new);
    }

    @Override
    public void create(CartCreReqDTO cartCreReqDTO) {
        String fullName = cartCreReqDTO.getFullName();
        String phone = cartCreReqDTO.getPhone();
        String email = cartCreReqDTO.getEmail();
        Optional<Customer> customerOptional=customerRepository.findCustomerByPhoneAndEmailAndAndFullName(phone,email ,fullName);
        Customer customer = new Customer();
        if (customerOptional.isPresent()){
            customer = customerOptional.get();
            // set :dia chi, tên ng sdt //=> = cus
            List<LocationRegion> locationRegions = customer.getLocationRegions();
            locationRegions.add(cartCreReqDTO.getLocationRegion().toLocationRegion());
            customer.setLocationRegions(locationRegions);
        }else {
            customer.setFullName(cartCreReqDTO.getFullName());
            customer.setEmail(cartCreReqDTO.getEmail());
            customer.setPhone(cartCreReqDTO.getPhone());
            List<LocationRegion> locationRegions = customer.getLocationRegions();
            locationRegions.add(cartCreReqDTO.getLocationRegion().toLocationRegion());
            customer.setLocationRegions(locationRegions);
            customerRepository.save(customer);
        }
        ECartStatus status = ECartStatus.getECartStatus(cartCreReqDTO.getStatus());
        Cart cart = new Cart();
        cart.setName_receiver(cartCreReqDTO.getFullName());
        cart.setPhone_receiver(cartCreReqDTO.getPhone());
        cart.setStatus(status);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setCustomer(customer);
        cartRepository.save(cart);

        Optional<LocationRegion> locationRegion = locationRegionRepository.findById(cartCreReqDTO.getLocationRegion().getId());
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


        List<CartDetail> cartDetailList = cartDetailRepository.saveAll(cartDetails);


        cart.setTotalAmount(totalTotal);
        cart.setLocationRegion(locationRegion.get());

        cart.setCartDetails(cartDetailList);
        cartRepository.save(cart);
    }

    @Override
    public Optional<CartDTO> getCartDTOByIdDeletedIsFalse(Long id) {
        return cartRepository.getCartDTOByIdDeletedIsFalse(id);
    }

    @Override
    public CartListResponse update(CartUpReqDTO cartUpReqDTO) {
        Optional<Cart>optionalCart = cartRepository.findById(cartUpReqDTO.getId());
        if (!optionalCart.isPresent()){
            throw new ResourceNotFoundException("Not found cart ");
        }
        Cart cart = optionalCart.get();
        // set lại các field người nhận.
        // remove cartdetail
        // add cartdetail.
        // update cart.



        String fullName = cartUpReqDTO.getFullName();
        String phone = cartUpReqDTO.getPhone();
        String email = cartUpReqDTO.getEmail();
        Optional<Customer> customerOptional=customerRepository.findCustomerByPhoneAndEmailAndAndFullName(phone,email ,fullName);
        Customer customer = customerOptional.get();
        if (customerOptional.isPresent()) {
            customer.setFullName(cartUpReqDTO.getFullName());
            customer.setEmail(cartUpReqDTO.getEmail());
            customer.setPhone(cartUpReqDTO.getPhone());
            customerRepository.save(customer);
        }
        ECartStatus status = ECartStatus.getECartStatus(cartUpReqDTO.getStatus());

        cart.setName_receiver(customer.getFullName());
        cart.setPhone_receiver(customer.getPhone());
        cart.setStatus(status);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setCustomer(customer);
        cartRepository.save(cart);

        Optional<LocationRegion> locationRegion = locationRegionRepository.findById(cartUpReqDTO.getLocationRegion().getId());
        List<CartDetailCreReqDTO> cartDetailCreReqDTOs = cartUpReqDTO.getCartDetailDTOList();
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


        List<CartDetail> cartDetailList = cartDetailRepository.saveAll(cartDetails);


        cart.setTotalAmount(totalTotal);
        cart.setLocationRegion(locationRegion.get());

        cart.setCartDetails(cartDetailList);
        cartRepository.save(cart);
        CartListResponse cartUpResDTO = new CartListResponse(cart);
        return cartUpResDTO;
    }
}
