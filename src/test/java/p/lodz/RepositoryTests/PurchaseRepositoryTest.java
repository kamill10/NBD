package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.*;
import p.lodz.Managers.ProductManager;
import p.lodz.Managers.PurchaseManager;
import p.lodz.Model.*;
import p.lodz.Model.Type.Premium;
import p.lodz.Repositiories.*;
import p.lodz.Repositiories.CassandraImplementations.ProductRepository;
import p.lodz.Repositiories.CassandraImplementations.PurchaseRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PurchaseRepositoryTest {


    Client client2 = new Client("Adnreas","Kaczka","premium",new Address("d","s","12"));
    Product product = new Product("Test purchase",69,10,"sportowe");
    CassandraConfig config = new CassandraConfig();
    ProductManager productManager = new ProductManager(config.getSession());
    PurchaseRepository purchaseRepository = new PurchaseRepository(config.getSession(), (ProductRepository) productManager.getProductRepository());
    Purchase purchase = new Purchase(client2,product);

    @Test
    public void testSavePurchase() {
        productManager.registerProduct(product);
        Purchase savedPurchase = purchaseRepository.savePurchase(purchase);
        assertNotNull(savedPurchase.getId());
        Purchase retrievedPurchase = purchaseRepository.findByClientId(client2.getId()).get(0);

        assertEquals(client2.getId(), retrievedPurchase.getClient());
        assertEquals(product.getId(), retrievedPurchase.getProducts());
        assertEquals(purchase.getPurchaseDate(), retrievedPurchase.getPurchaseDate());
        assertEquals(purchase.getDeliveryDate(), retrievedPurchase.getDeliveryDate());
        assertEquals(purchase.getFinalCost(), retrievedPurchase.getFinalCost());
    }

    @Test
    public void testFindByClientId() {
        productManager.registerProduct(product);
        purchaseRepository.savePurchase(purchase);

        List<Purchase> purchases = purchaseRepository.findByClientId(client2.getId());
        assertNotNull(purchases);
        assertFalse(purchases.isEmpty());
        Purchase retrievedPurchase = purchases.get(0);
        assertEquals(client2.getId(), retrievedPurchase.getClient());
        assertEquals(product.getId(), retrievedPurchase.getProducts());
        assertEquals(purchase.getPurchaseDate(), retrievedPurchase.getPurchaseDate());
        assertEquals(purchase.getDeliveryDate(), retrievedPurchase.getDeliveryDate());
        assertEquals(purchase.getFinalCost(), retrievedPurchase.getFinalCost());
    }

    @Test
    public void testFindByProductId() {
        productManager.registerProduct(product);
        purchaseRepository.savePurchase(purchase);
        List<Purchase> purchases = purchaseRepository.findByProductId(product.getId());
        assertNotNull(purchases);
        assertFalse(purchases.isEmpty());
        Purchase retrievedPurchase = purchases.get(0);
        assertEquals(client2.getId(), retrievedPurchase.getClient());
        assertEquals(product.getId(), retrievedPurchase.getProducts());
        assertEquals(purchase.getPurchaseDate(), retrievedPurchase.getPurchaseDate());
        assertEquals(purchase.getDeliveryDate(), retrievedPurchase.getDeliveryDate());
        assertEquals(purchase.getFinalCost(), retrievedPurchase.getFinalCost());
    }

    @Test
    public void testEndPurchase() {
        Client client2 = new Client("Adnreas","Kaczka","premium",new Address("d","s","12"));
        Product product = new Product("Zmniejszy",69,10,"sportowe");
        Purchase purchaseTest= new Purchase(client2,product);
        productManager.registerProduct(product);
        purchaseRepository.savePurchase(purchaseTest);
        LocalDate data = purchaseTest.getDeliveryDate();
        purchaseRepository.endPurchase(purchase);

        Purchase endedPurchase = purchaseRepository.findByClientId(client2.getId()).get(0);
        assertFalse(endedPurchase.getDeliveryDate().isAfter(data));



    }

}