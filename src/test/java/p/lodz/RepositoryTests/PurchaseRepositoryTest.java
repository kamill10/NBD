package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.Model.*;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Repositiories.*;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseRepositoryTest {

    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase purchaseDatabase = repository.getDatabase();
    private static Client testClient1 =  new Client("jan", "kowalski", new Address("aaa", "bbb", "ccc"),new Premium());
    private static Client testClient2 =  new Client("janusz", "testowny", new Address("aaa", "bbb", "ccc"),new Premium());

    static Product product = new Product("aaa", 1, 5, "aaa");
    static Product product2 = new Product("buty", 1, 3, "luksusowe buty arktyczne");
    static PurchaseRepositoryMongoDB purchaseRepository  = new PurchaseRepositoryMongoDB(purchaseDatabase.getCollection("purchases_test",Purchase.class));
    List<ProductEntry> products = new ArrayList<>();
    Purchase purchase;

    @BeforeEach
    public void setUp() {
        // Add products to the list in the setup method
        products.add(new ProductEntry(product, 1));
        products.add(new ProductEntry(product2, 3));

         purchase = new Purchase(testClient1,products);
        System.out.println("Products list: " + products);
    }

    @Test
    void savePurchaseTest() {
        System.out.println(testClient1);
        System.out.println(products.isEmpty());
        System.out.println(purchase);
        System.out.println(purchase.getEntityId());
        assertEquals(purchaseRepository.savePurchase(purchase).getEntityId(),purchase.getEntityId());
        assertEquals(purchaseRepository.findAllPurchases().size(),1);
    }
   @Test
    void findPurchaseById(){
       assertEquals(purchase.getEntityId(),purchaseRepository.findPurchaseById(purchase.getEntityId()).getEntityId());
    assertEquals(purchaseRepository.findPurchaseById(purchase.getEntityId()).getProducts().size(),2);
    }
    @AfterAll
    static void cleanDataBase(){
        assertEquals(purchaseRepository.findAllClientPurchases(testClient2).size(),0);

        assertEquals(purchaseRepository.findAllClientPurchases(testClient1).size(),1);

        assertEquals(purchaseRepository.findAllPurchases().size(),1);
        /*Document command = new Document("replSetStepDown", 60);
        purchaseDatabase.runCommand(command);
        assertEquals(purchaseRepository.findAllClientPurchases(testClient2).size(),0);

        assertEquals(purchaseRepository.findAllClientPurchases(testClient1).size(),1);

        assertEquals(purchaseRepository.findAllPurchases().size(),1); */
        purchaseDatabase.getCollection("purchases_test").drop(); // Remove the collection

    }
}