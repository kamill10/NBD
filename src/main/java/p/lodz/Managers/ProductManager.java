package p.lodz.Managers;


import com.datastax.oss.driver.api.core.CqlSession;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Model.Product;
import p.lodz.Repositiories.CassandraImplementations.ProductRepository;

import java.util.List;
import java.util.UUID;

public class ProductManager {
    private final p.lodz.Repositiories.ProductRepository productRepository;

    public ProductManager(CqlSession session) {
        this.productRepository = new ProductRepository(session);
    }

    public Product getProduct(UUID id){
        return productRepository.findProductById(id);
    }

    public Product registerProduct(String productName, double baseCost, int numberOfProducts, String description){
        Product product = new Product(baseCost, description, numberOfProducts, productName);
        return productRepository.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

}
