package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.bson.Document;
import org.junit.jupiter.api.*;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PurchaseRepositoryTest {

    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase purchaseDatabase = repository.getDatabase();
    private static Client testClient1 =  new Client("jan", "kowalski", new Address("aaa", "bbb", "ccc"),new Premium());
    private static  Client testClient2 =  new Client("zdichu", "mulat", new Address("pcim", "dolny", "ccc"),new Premium());

    static Product product = new Product("aaa", 10, 2, "aaa");
    static Product product2 = new Product("buty", 20, 3, "luksusowe buty arktyczne");
    static PurchaseRepositoryMongoDB purchaseRepository  = new PurchaseRepositoryMongoDB(purchaseDatabase.getCollection("purchases_test",Purchase.class));
    Purchase purchase1 = new Purchase(testClient1,new ProductEntry(product, 2));
    Purchase purchase2 = new Purchase(testClient2,new ProductEntry(product2, 3));

    @Test
    @Order(1)
    void savePurchaseTest() {
        Purchase purchase = purchaseRepository.savePurchase(purchase1);
        assertEquals(purchase.getEntityId(),purchase1.getEntityId());
    }
   @Test
   @Order(2)
    void findPurchaseById(){
       purchaseRepository.savePurchase(purchase2);
       assertEquals(purchase2.getEntityId(),purchaseRepository.findPurchaseById(purchase2.getEntityId()).getEntityId());
   }
   @Test
   @Order(3)
   void findAllClientPurchase(){
       assertEquals(purchaseRepository.findAllClientPurchases(testClient1).size(),1);
   }

    @Test
    @Order(4)
    void findAllPurchases(){
        assertEquals(purchaseRepository.findAllPurchases().size(), 2);
    }

    @Test
    @Order(5)
    void primaryNodeDisabled() {
        Document command = new Document("replSetStepDown", 60);
        purchaseDatabase.runCommand(command);
        assertEquals(purchaseRepository.findAllPurchases().size(), 2);
        List<Purchase> purchases = purchaseRepository.findAllPurchases();
        assertEquals(purchases.size(), 2);
        double finalCost = purchases.stream()
                .mapToDouble(Purchase::getFinalCost)
                .sum();
        assertEquals(finalCost, 80);
    }

    @AfterAll
    static void cleanDataBase(){
        purchaseDatabase.getCollection("purchases_test").drop(); // Remove the collection
        repository.close();
    }
}