package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import p.lodz.Mappers.ProductMapper;
import p.lodz.Model.Product;
import p.lodz.Repositiories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryMongoImpl implements ProductRepository {
    private MongoCollection<Document> mongoCollection;
    public ProductRepositoryMongoImpl(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public ProductRepositoryMongoImpl() {}
    @Override
    public Product saveProduct(Product product) {
        mongoCollection.insertOne(ProductMapper.toMongoProduct(product));
        return product;
    }

    @Override
    public Product archiveProduct(ObjectId id) {
        Document afterUpdate = mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.set("archived", true),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        if(afterUpdate == null) return null;
        return ProductMapper.fromMongoProduct(afterUpdate);
    }

    @Override
    public Product decrementNumberOfProducts(ObjectId id) {
        Document afterUpdate = mongoCollection.findOneAndUpdate(Filters.eq("_id", id),
                Updates.inc("number_of_products", -1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        if(afterUpdate == null) return null;
        return ProductMapper.fromMongoProduct(afterUpdate);
    }

    @Override
    public Product findProductById(ObjectId id) {
        Document product = mongoCollection.find(Filters.eq("_id", id)).first();
        if(product == null) return null;
        return ProductMapper.fromMongoProduct(product);
    }

    @Override
    public List<Product> findAllProducts() {
        List<Document> productDocuments = mongoCollection.find().into(new ArrayList<>());
        List<Product> products = new ArrayList<>();
        productDocuments.forEach(element -> {
            products.add(ProductMapper.fromMongoProduct(element));
        });
        return products;
    }
}
