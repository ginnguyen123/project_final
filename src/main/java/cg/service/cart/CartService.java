package cg.service.cart;

import cg.dto.cart.*;
import cg.dto.cartDetail.CartDetailCreReqDTO;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.dto.report.ProductReportDTO;
import cg.dto.report.DayToDayReportDTO;
import cg.dto.report.ReportDTO;
import cg.dto.report.YearReportDTO;
import cg.exception.DataInputException;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.customer.Customer;
import cg.model.enums.ECartStatus;
import cg.model.enums.EColor;
import cg.model.enums.ESize;
import cg.model.location_region.LocationRegion;
import cg.model.product.Product;
import cg.model.user.User;
import cg.repository.*;
import cg.service.cart.response.CartListResponse;
import cg.service.cartDetail.ICartDetailService;
import cg.service.products.IProductService;
import cg.utils.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class CartService implements ICartService {
    @Autowired
    ICartDetailService cartDetailService;

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
    public Optional<Cart> findCartsByUserAndStatusIsCart(User user, ECartStatus status) {
        return cartRepository.findCartsByUserAndStatusIsCart(user, status);
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
    public Cart create(CartCreReqDTO cartCreReqDTO) {
        String email = cartCreReqDTO.getEmail();
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
        Customer customer = new Customer();
        LocationRegion locationRegion = cartCreReqDTO.getLocationRegion().toLocationRegion(customer);
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
            Long idLocationRegion = cartCreReqDTO.getLocationRegion().getId();
            Optional<LocationRegion> optionalLocationRegion = locationRegionRepository.findById(idLocationRegion);
            if (optionalLocationRegion.isPresent()) {
                List<LocationRegion> locationRegionsOfCustomer = customer.getLocationRegions();
                LocationRegion lcReve = locationRegionsOfCustomer.stream().filter(i -> Objects.equals(i.getId(), idLocationRegion)).collect(Collectors.toList()).get(0);
                locationRegion = lcReve;
            }
        } else if (customerOptional.isEmpty()) {
            customer.setEmail(cartCreReqDTO.getEmail());
            customer.setUser(new User());
            customerRepository.save(customer);
            locationRegion.setId(null);
            locationRegionRepository.save(locationRegion);
        }
//        ECartStatus status = ECartStatus.getECartStatus("UNPAID");
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
        cart.setCartDetails(cartDetails);
        cart.setTotalAmount(totalTotal);
        cart.setLocationRegion(locationRegion);
        cart.setCustomer(customer);
        cartRepository.save(cart);
        cartDetailRepository.saveAll(cartDetails);
        return cart;
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
        Long discount = product.getDiscount().getDiscount();
        BigDecimal totalAmountPerProduct = product.getPrice().subtract((product.getPrice().multiply(BigDecimal.valueOf(discount))).divide(BigDecimal.valueOf(100)));
        BigDecimal totalAmount = totalAmountPerProduct.multiply(BigDecimal.valueOf(cartCreMiniCartReqDTO.getQuantity()));
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
    public CartUpReqDTO getCartDTOByCartDetail(CartUpReqDTO cartUpReqDTO) {
        Cart oldCart = cartRepository.findById(cartUpReqDTO.getId()).get();
        List<CartDetail> newCartDetail = cartUpReqDTO.toCart().getCartDetails();
        oldCart.setCartDetails(newCartDetail);
        oldCart.setName_receiver(cartUpReqDTO.getName_receiver());
        oldCart.setPhone_receiver(cartUpReqDTO.getPhone_receiver());
        oldCart.setLocationRegion(cartUpReqDTO.toCart().getLocationRegion());
        oldCart.setTotalAmount(cartUpReqDTO.getTotalAmount());
        oldCart.setStatus(cartUpReqDTO.toCart().getStatus());
        Cart upCart = cartRepository.save(oldCart);


        return upCart.toCartUpReqDTO();
    }

    @Override
    public ReportDTO getReportOfDay(String day) {
        return cartRepository.getReportOfDay(day);
    }

    @Override
    public List<DayToDayReportDTO> getReportFromDayToDay(String startDay, String endDay) {
        return cartRepository.getReportFromDayToDay(startDay, endDay);
    }

    @Override
    public List<ProductReportDTO> getBestSeller(Pageable pageable) {
        Page<ProductReportDTO> page = cartRepository.getBestSeller(pageable);
        return page.getContent();
    }

    @Override
    public List<YearReportDTO> getReportByYear(int year) {
        return cartRepository.getReportByYear(year);
    }

    @Override
    public YearReportDTO getReportByMonth(int year, int month) {
        return cartRepository.getReportByMonth(year,month);
    }

    @Override
    public List<ProductReportDTO> getTop5ProductUnMarketTableCurrentMonth(Pageable pageable) {
        Page<ProductReportDTO> page = cartRepository.getTop5ProductUnMarketTableCurrentMonth(pageable);
        return page.getContent();
    }

    @Override
    public CartUpResDTO update(CartUpReqDTO cartUpReqDTO) {
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
            Long idCartDetail = item.getId();
            CartDetail cartDetail = item.toCartDetail();
            Optional<Product> product = productRepository.findById(item.getProductId());
            BigDecimal price = product.get().getPrice();
            long quantity = item.getQuantity();
            BigDecimal totalLe = price.multiply(BigDecimal.valueOf(quantity));
            cartDetail.setTotalAmount(totalLe);
            totalTotal = totalTotal.add(totalLe);
            cartDetail.setId(idCartDetail);
            cartDetail.setProduct(product.get());
            cartDetail.setCart(cart);
            cartDetails.add(cartDetail);
        }

        cart.setTotalAmount(totalTotal);
        Optional<LocationRegion> locationRegion = locationRegionRepository.findById(cartUpReqDTO.getLocationRegion().getId());
        cart.setLocationRegion(locationRegion.get());
        cart.setCartDetails(cartDetails);
        cartDetailRepository.saveAll(cartDetails);
        cartRepository.save(cart);

        CartUpResDTO cartUpResDTO = new CartUpResDTO(cart);
        return cartUpResDTO;
    }

    @Override
    public Cart findCartsByCustomerIdAndStatusIsCart(Long customerId, ECartStatus status) {
        return cartRepository.findCartsByCustomerIdAndStatusIsCart(customerId,status);
    }
    public BigDecimal getTotalAmountCart(Cart cart) {
        List<CartDetail> cartDetailList = cartDetailService.findCartDetailsByCartAndDeletedIsFalse(cart);
        BigDecimal totalAmountCart = BigDecimal.ZERO;
        for (CartDetail item : cartDetailList) {
            totalAmountCart = item.getTotalAmount().add(totalAmountCart);
        }
        return totalAmountCart;
    }
    public Date getDateByString(String dateString){
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Parse the input date string into a Date object
            Date date = inputDateFormat.parse(dateString);

            // Format the Date object back into a string
            String formattedDate = outputDateFormat.format(date);

            System.out.println("Original date string: " + dateString);
            System.out.println("Parsed Date: " + date);
            System.out.println("Formatted date string: " + formattedDate);
            return date;
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing the date: " + e.getMessage());
            return null;
        }

    }

}
