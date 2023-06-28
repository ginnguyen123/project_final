package cg.repository;

import cg.model.cart.Cart;
import cg.model.cart.CartDetail;
import cg.utils.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface CartFilterRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

    default Page<Cart> findAllByFilters(CartRequest keyword, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
//         ManyToOne

            if(keyword.getKeyword() !=null) {
                Predicate predicateCusName = criteriaBuilder.like(root.get("customer").get("fullName"), '%' + keyword.getKeyword() + '%');
                Predicate predicateAddress = criteriaBuilder.like(root.get("locationRegion").get("address"),'%' + keyword.getKeyword() + '%');
                Predicate predicateKw = criteriaBuilder.or(predicateCusName, predicateAddress);
                predicates.add(predicateKw);
            }

            SetJoin<Cart, CartDetail> cartDetailSet = root.joinSet("cartDetails", JoinType.LEFT);
            Predicate ProductTitlepredicate = criteriaBuilder.like(cartDetailSet.get("products").get("title"), '%' + keyword.getKeyword() + '%');
            predicates.add(ProductTitlepredicate);




            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);

    }


}