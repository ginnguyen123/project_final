package cg.service.products;

import cg.model.product.Product;
import cg.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product,Long> {
    List<Product> saveAll(List<Product> products);
}
