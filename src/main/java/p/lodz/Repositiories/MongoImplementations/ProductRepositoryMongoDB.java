package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import p.lodz.Model.Product;
import p.lodz.Repositiories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRepositoryMongoDB implements ProductRepository {
    private final MongoCollection<Product> mongoCollection;
    public ProductRepositoryMongoDB(MongoCollection<Product> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    @Override
    public Product saveProduct(Product product) {
        mongoCollection.insertOne(product);
        return product;
    }

    @Override
    public Product archiveProduct(UUID id, boolean value) {
        return mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.set("archived", value),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
    }

    @Override
    public Product decrementNumberOfProducts(UUID id, int quantity) {
        return mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.inc("number_of_products", -quantity),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
    }

    @Override
    public Product findProductById(UUID id) {
        return mongoCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<Product> findAllProducts() {
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public boolean deleteProduct(UUID id) {
        Product deletedProduct = mongoCollection.findOneAndDelete(Filters.eq("_id", id));
        return deletedProduct != null;
    }
}
