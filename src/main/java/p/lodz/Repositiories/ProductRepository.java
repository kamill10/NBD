package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Product;

import java.util.List;

public interface ProductRepository {
    Product saveProduct(Product product);
    Product archiveProduct(ObjectId id, boolean value);
    Product decrementNumberOfProducts(ObjectId id, int quantity);
    Product findProductById(ObjectId id);
    List<Product> findAllProducts();
    boolean deleteProduct(ObjectId id);
}
