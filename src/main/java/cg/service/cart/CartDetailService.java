package cg.service.cart;

import cg.model.cart.CartDetail;
import cg.repository.CartDetailRepository;
import cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartDetailService implements ICartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Override
    public List<CartDetail> findAll() {
        return null;
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void delete(CartDetail cartDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
