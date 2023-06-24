package cg.service.cart;

import cg.dto.cart.CartCreReqDTO;
import cg.dto.cart.CartCreResDTO;
import cg.dto.cart.CartDTO;
import cg.dto.cartDetail.CartDetailDTO;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.customer.Customer;
import cg.model.enums.ESize;
import cg.model.product.Product;
import cg.repository.CartFilterRepository;
import cg.repository.CartRepository;
import cg.repository.CustomerRepository;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService implements ICartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartFilterRepository cartFilterRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void delete(Cart cart) {

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
    public CartCreResDTO create(CartCreReqDTO cartCreReqDTO) {
        Long idCus = cartCreReqDTO.getCustomerId();
        Optional<Customer> optionalCustomer = customerRepository.findById(idCus);

        if (!optionalCustomer.isPresent()){
            Customer cusNew = new Customer();
            cusNew.setFullName(cartCreReqDTO.getFullName());
            cusNew.setEmail(cartCreReqDTO.getEmail());
            cusNew.setPhone(cartCreReqDTO.getPhone());
        }else {
            Customer customer = optionalCustomer.get();
            customer.setFullName(customer.getFullName());
            customer.setEmail(customer.getEmail());
            customer.setPhone(customer.getPhone());
        }



//        List<CartDetailDTO> cartDetails = cartCreReqDTO.getCartDetailDTOList();
//        long totalAmountCart = 0;
//        for (CartDetailDTO cartDetailDTO : cartDetails){
//            CartDetail cartDetail = new CartDetail();
//            cartDetail.setSize(cartDetailDTO.getSize());
//            cartDetail.setColor(cartDetailDTO.getColor());
////            cartDetail.setPrice(cartDetailDTO.getProduct().getPrice());
//            cartDetail.setQuantity(cartDetailDTO.getQuantity());
//            cartDetails.add(cartDetail.toaCartDetailDTO());
//            totalAmountCart += Long.valueOf(String.valueOf(cartDetailDTO.getProduct().getPrice()))  * cartDetailDTO.getQuantity();
//        }

//        Cart cart = new Cart();
//        cart.setCustomer(customer);
//        cart.setCartDetails(cartDetails.toC)

//        CartDTO cartDTO = new CartDTO();
//        cartDTO.setCartDetailDTOList(cartDetails);
//        Cart cart = new Cart();
//        cart.setCustomer(customer);
//        cart.setTotalAmount(BigDecimal.valueOf(totalAmountCart));
//        cart.setCartDetails(cartDTO.toCart().getCartDetails());

        return null;
    }
}
