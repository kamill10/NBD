package p.lodz.Managers;


import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Model.Product;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

public class ProductManager {
    private final ProductRepository productRepository;

    public ProductManager(MongoCollection<Product> collection) {
        this.productRepository = new ProductRepositoryMongoDB(collection);
    }

    public Product getProduct(ObjectId id){
        return productRepository.findProductById(id);
    }

    public Product registerProduct(String productName, double baseCost, int numberOfProducts, String description){
        Product product = new Product(productName, baseCost, numberOfProducts, description);
        return productRepository.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

}
