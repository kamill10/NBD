package p.lodz.Repositiories;

import p.lodz.Model.Product;

import java.util.List;

public interface ProductRepository {
    Product saveProduct(Product product);
    Product archiveProduct(Long id);
    Product decrementNumberOfProducts(Long id);
    Product findProductById(Long id);
    List<Product> findAllProducts();
}
