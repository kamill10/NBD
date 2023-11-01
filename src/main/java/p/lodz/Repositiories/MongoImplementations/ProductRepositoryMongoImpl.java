package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import p.lodz.Mappers.ProductMapper;
import p.lodz.Model.Product;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

public class ProductRepositoryMongoImpl implements ProductRepository {
    private final MongoCollection<Document> mongoCollection;
    public ProductRepositoryMongoImpl(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }
    @Override
    public Product saveProduct(Product product) {
        mongoCollection.insertOne(ProductMapper.toMongoProduct(product));
        return product;
    }

    @Override
    public Product archiveProduct(Long id) {
        return null;
    }

    @Override
    public Product decrementNumberOfProducts(Long id) {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return null;
    }
}
