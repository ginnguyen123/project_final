package cg.service.cartDetail;

import cg.dto.cartDetail.CartDetailDTO;
import cg.model.cart.CartDetail;
import cg.repository.CartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CartDetailService implements ICartDetailService{


    @Autowired
    CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> findAll() {
        return null;
    }

    @Override
    public List<CartDetail> findAllByCart_IdAndAndDeletedIsFalse(Long id) {
        return cartDetailRepository.findAllByCart_IdAndAndDeletedIsFalse(id);
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
        return null;
    }

    @Override
    public void delete(CartDetail cartDetail) {
        cartDetail.setDeleted(true);
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public void deleteById(Long id) {

    }
}
