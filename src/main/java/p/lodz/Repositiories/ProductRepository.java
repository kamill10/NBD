package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    void  saveProduct(Product product);
    void update(Product product);
    Product findProductById(UUID id);
    boolean deleteProduct(Product product);

    /*boolean archiveProduct(Product product , UUID id , boolean archived);
    boolean decrementProducts(Product product , UUID id , int numberOfProducts); */
}
