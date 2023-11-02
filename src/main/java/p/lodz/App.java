package p.lodz;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import p.lodz.Model.*;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoImpl;
import p.lodz.Repositiories.ProductRepository;

public class App {
    public static void main(String[] args) {
        final ConnectionString connectionString = new ConnectionString("mongodb://admin:adminpassword@localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set_single");
        final MongoClient mongoClient = MongoClients.create(connectionString);
        final MongoDatabase mongoDatabase = mongoClient.getDatabase("nbddb");
        ProductRepository  productRepository = new ProductRepositoryMongoImpl(mongoDatabase.getCollection("product"));
        Product product = new Product("bbb", 10, 1, "aabb");
        productRepository.saveProduct(product);


//        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
//            EntityManager em = emf.createEntityManager();
//            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
//            Validator validator = validatorFactory.getValidator();
//            Shop shop = new Shop(em, validator);
//            shop.getClientManager().registerClient("null", "bbb", "wwa", "ullica", "10", new Standard());
//            shop.getProductManager().registerProduct("aaa", 10, 1, "aabb");
//            shop.getClientManager().registerClient("aaa", "bbbb", "wwa", "ullica", "10", new Standard());
//            shop.getPurchaseManager().registerPurchase(shop.getClientManager().getClient(1L), shop.getProductManager().getAllProducts());
//            shop.getProductManager().registerProduct("bbb", 10, 1, "ccdd");
//            shop.getClientManager().registerClient("bbb", "cccc", "wwa", "ullica", "10", new Standard());
//            shop.getPurchaseManager().registerPurchase(shop.getClientManager().getClient(2L), shop.getProductManager().getProduct(2L));
//        }
    }
}
