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

    public void  registerProduct(Product product){
        productRepository.saveProduct(product);
    }
    public boolean deleteProduct(Product product ){
        return productRepository.deleteProduct(product);
    }
    public void updateProduct(Product product){
        productRepository.update(product);
    }


}
