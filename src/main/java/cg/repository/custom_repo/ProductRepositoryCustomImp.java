package cg.repository.custom_repo;

import cg.dto.product.client.FilterRes;
import cg.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImp{

    public Page<Product> findAllProductByFilter(FilterRes filter, Pageable pageable) {
        List<String> colors = filter.getColors();
        List<String> sizes = filter.getColors();
        List<List<Long>> minMaxPrices = filter.getMinMax();
        Long id = filter.getId();

        String sql = "SELECT prod.id, prod.created_at, prod.created_by, prod.deleted, prod.update_at, prod.update_by, " +
                "prod.discount_id, prod.code, prod.description, prod.prices, prod.title, prod.brand_id, prod.category_id," +
                "prod.product_avatar_id " +
                "FROM products AS prod " +
                "INNER JOIN product_import AS imp ON imp.product_id = prod.id " +
                "LEFT JOIN category AS cate ON cate.id = prod.category_id " +
                "LEFT JOIN discounts AS disc ON disc.id = prod.discount_id " +
                "WHERE prod.deleted = 0 " +
                "AND prod.category_id = ? " +
                "AND imp.quantity > 0 " +
                "AND (:today BETWEEN disc.start_date AND disc.end_date OR prod.discount_id IS NULL) ";

        if (minMaxPrices.size() != 0){
            sql += "AND ";
            for (int i = 0; i < minMaxPrices.size(); i++){
                    Long min = minMaxPrices.get(i).get(0);
                    Long max = minMaxPrices.get(i).get(1);
                    sql += "prod.prices BETWEEN ? AND ? ";
                    if (i != minMaxPrices.size() - 1)
                        sql += "OR ";

            }
        }

        return null;
    }
}
