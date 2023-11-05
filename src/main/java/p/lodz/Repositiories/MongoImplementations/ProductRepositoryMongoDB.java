package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import p.lodz.Model.Product;
import p.lodz.Repositiories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

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
    public Product archiveProduct(ObjectId id) {
        return mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.set("archived", true),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
    }

    @Override
    public Product decrementNumberOfProducts(ObjectId id, int quantity) {
        Product product = mongoCollection.find(Filters.eq("_id", id)).first();
        if(product.getNumberOfProducts() -quantity < 0) {
            throw new RuntimeException("The number of available products is to little");
        }
        else if(product.getNumberOfProducts() -quantity == 0) {
            Bson combinedUpdates = Updates.combine(
                    Updates.set("archived", true),
                    Updates.inc("number_of_products", -quantity));
            return mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                    combinedUpdates,
                    new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        }
        return mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.inc("number_of_products", -quantity),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
    }

    @Override
    public Product findProductById(ObjectId id) {
        return mongoCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<Product> findAllProducts() {
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public boolean deleteProduct(ObjectId id) {
        Product deletedProduct = mongoCollection.findOneAndDelete(Filters.eq("_id", id));
        return deletedProduct != null;
    }
}
