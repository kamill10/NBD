package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import p.lodz.Exceptions.InvalidDataException;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase productDatabase = repository.getDatabase();
    static ProductRepositoryMongoDB productRepository = new ProductRepositoryMongoDB(productDatabase.getCollection("products_test", Product.class));


    @Test
    void saveProductTest() {
        Product product = new Product("buty", 1, 1, "aaa");
        assertEquals(product, productRepository.saveProduct(product));
    }

    @Test
    void archiveProductTest() {
        Product savedProduct = productRepository.saveProduct(new Product("skora", 1, 1, "aaa"));
       Product product1 = productRepository.archiveProduct(savedProduct.getEntityId(), true);
        assertTrue(product1.isArchived());
    }

    @Test
    void decrementNumberOfProductTest() {
        Product savedProduct = productRepository.saveProduct(new Product("koszulka", 1, 1, "aaa"));
        Product productAfterDecrement = productRepository.decrementNumberOfProducts(savedProduct.getEntityId(), 1);
        assertEquals(0, productAfterDecrement.getNumberOfProducts());
<<<<<<< HEAD
        assertThrows(InvalidDataException.class, () -> productRepository.decrementNumberOfProducts(savedProduct.getEntityId(), 1));
        assertEquals(0, productRepository.findProductById(savedProduct.getEntityId()).getNumberOfProducts());
       // assertThrows(RuntimeException.class, () -> {productRepository.decrementNumberOfProducts(savedProduct.getEntityId());});
=======
        assertTrue(productAfterDecrement.isArchived());
        assertThrows(RuntimeException.class, () -> {productRepository.decrementNumberOfProducts(productAfterDecrement.getEntityId(),1);});
>>>>>>> 9a9b97a6fc829b1e603b368bd09866cf7371d9f5
    }

    @Test
    void findProductByIdTest() {
        Product savedProduct = productRepository.saveProduct(new Product("dres", 1, 1, "aaa"));
        assertEquals(savedProduct.getEntityId(), productRepository.findProductById(savedProduct.getEntityId()).getEntityId());
    }

    @Test
    void findAllProductsTest() {
        Product product = new Product("suknia", 1, 1, "aaa");
        Product product2 = new Product("talon", 1, 1, "aaa");
        productRepository.saveProduct(product);
        productRepository.saveProduct(product2);
    }

    @AfterAll
    static void cleanDataBase(){
        assertEquals(productRepository.findAllProducts().size(),6);
        //Document command = new Document("replSetStepDown", 60);
        //productDatabase.runCommand(command);
        assertEquals(productRepository.findAllProducts().size(),6);
        productDatabase.getCollection("products_test").drop(); // Remove the collection

    }




}