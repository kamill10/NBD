package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Repositiories.*;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseRepositoryTest {

    /*static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase purchaseDatabase = repository.getDatabase();
    private static Client testClient1 =  new Client("jan", "kowalski", new Address("aaa", "bbb", "ccc"),new Premium());
    private static  Client testClient2 =  new Client("zdichu", "mulat", new Address("pcim", "dolny", "ccc"),new Premium());

    static Product product = new Product("aaa", 1, 1, "aaa");
    static Product product2 = new Product("buty", 1, 1, "luksusowe buty arktyczne");
    static PurchaseRepositoryMongoDB purchaseRepository  = new PurchaseRepositoryMongoDB(purchaseDatabase.getCollection("purchases_test",Purchase.class));
    Purchase purchase1 = new Purchase(testClient1,product);
    Purchase purchase2 = new Purchase(testClient2,product2);

    @Test
    void savePurchaseTest() {
        assertEquals(purchaseRepository.savePurchase(purchase1).getEntityId(),purchase1.getEntityId());
    }
   @Test
    void findPurchaseById(){
        purchaseRepository.savePurchase(purchase2);
       assertEquals(purchase2.getEntityId(),purchaseRepository.findPurchaseById(purchase2.getEntityId()).getEntityId());
   }
   @Test
   void findAllClientPurchase(){
       assertEquals(purchaseRepository.findAllClientPurchases(testClient1).size(),1);
   }
    @AfterAll
    static void cleanDataBase(){
        assertEquals(purchaseRepository.findAllPurchases().size(),2);
        purchaseDatabase.getCollection("purchases_test").drop(); // Remove the collection

    } */
}