package p.lodz.Repositiories;

import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    Product saveProduct(Product product);
    Product archiveProduct(UUID id, boolean value);
    Product decrementNumberOfProducts(UUID id, int quantity);
    Product findProductById(UUID id);
    List<Product> findAllProducts();
    boolean deleteProduct(UUID id);
}
