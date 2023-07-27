package cg.service.cartDetail;

import cg.dto.cartDetail.CartDetailDTO;
import cg.dto.cartDetail.CartDetailNotCart;
import cg.dto.cartDetail.CartDetailUpReqDTO;
import cg.exception.ResourceNotFoundException;
import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.model.product.Product;
import cg.repository.CartDetailRepository;
import cg.repository.ProductImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CartDetailService implements ICartDetailService{
    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductImportRepository productImportRepository;

    @Override
    public List<CartDetail> findAll() {
        return null;
    }


//    @Override
//    public Long getCartDetailWithProductAndSizeAndColor(ESize size , EColor color , Long idProduct) {
//        Long quantityImp = productImportRepository.checkQuantityProductImportBySizeAndColor(idProduct,color,size);
//        //ajax -> check so luong -> data = 123
//
//        return quantityImp;
//    }

    @Override
    public Long getCartDetailWithProduct(Long idProduct) {
        Long quantityImp = productImportRepository.checkQuantityProductImport(idProduct);
        return quantityImp;
    }

    @Override
    public List<CartDetail> findAllByCart_IdAndDeletedIsFalse(Long id) {
        return cartDetailRepository.findAllByCart_IdAndDeletedIsFalse(id);
    }

    public List<CartDetail> findCartDetailsByCartAndDeletedIsFalse(Cart cart) {
        return cartDetailRepository.findCartDetailsByCartAndDeletedIsFalse(cart);
    }



    @Override
    public Optional<CartDetailDTO> getByIdAndDeletedIsFalse(Long id) {
        return cartDetailRepository.getByIdAndDeletedIsFalse(id);
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public List<CartDetail> saveAll(List<CartDetail> cartDetails) {
        return cartDetailRepository.saveAll(cartDetails);
    }

    @Override
    public void delete(CartDetail cartDetail) {
        cartDetail.setDeleted(true);
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public CartDetailNotCart update(CartDetailUpReqDTO cartDetailUpReqDTO) {
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findById(cartDetailUpReqDTO.getId());
        if (!optionalCartDetail.isPresent()) {
            throw new ResourceNotFoundException("Not found cartDetail ");
        }
        CartDetail cartDetail = optionalCartDetail.get();
        cartDetail.setCart(cartDetailUpReqDTO.toCartDetail().getCart());
        cartDetail.setProduct(cartDetailUpReqDTO.toCartDetail().getProduct());
        cartDetail.setTotalAmount(cartDetailUpReqDTO.toCartDetail().getTotalAmount());
        cartDetail.setColor(cartDetailUpReqDTO.getColor());
        cartDetail.setSize(cartDetailUpReqDTO.getSize());
        cartDetail.setQuantity(cartDetailUpReqDTO.getQuantity());
        cartDetailRepository.save(cartDetail);
        return new CartDetailNotCart(cartDetail);
    }

    @Override
    public BigDecimal getTotalAmountCartDetail(Product product, Long new_quantity) {
        Long discount = product.getDiscount().getDiscount();
        BigDecimal totalAmountPerProduct = product.getPrice().subtract((product.getPrice().multiply(BigDecimal.valueOf(discount))).divide(BigDecimal.valueOf(100)));
        BigDecimal totalAmount = totalAmountPerProduct.multiply(BigDecimal.valueOf(new_quantity));
        return totalAmount;
    }

    @Override
    public BigDecimal totalAmoutByCart(Cart cart) {
        return cartDetailRepository.totalAmoutByCart(cart);
    }
}
