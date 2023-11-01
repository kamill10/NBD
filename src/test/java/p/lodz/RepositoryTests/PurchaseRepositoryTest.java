package p.lodz.RepositoryTests;

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
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;
import p.lodz.Repositiories.Implementations.ClientRepositoryImpl;
import p.lodz.Repositiories.Implementations.ClientTypeRepositoryImpl;
import p.lodz.Repositiories.Implementations.ProductRepositoryImpl;
import p.lodz.Repositiories.Implementations.PurchaseRepositoryImpl;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseRepositoryTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static PurchaseRepository purchaseRepository;
    private static ClientRepository clientRepository;
    private static ProductRepository productRepository;
    private static ClientTypeRepository clientTypeRepository;

    @BeforeAll
    static void initTest() {
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
        purchaseRepository = new PurchaseRepositoryImpl(em);
        clientRepository = new ClientRepositoryImpl(em);
        productRepository = new ProductRepositoryImpl(em);
        clientTypeRepository = new ClientTypeRepositoryImpl(em);
    }

    @Test
    void savePurchaseTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        Client savedClient = clientRepository.saveClient(client);
        Product product = new Product("aaa", 1, 1, "aaa");
        Product savedProduct = productRepository.saveProduct(product);
        Purchase purchase = new Purchase(savedClient, new ArrayList<Product>() {
            {
                add(savedProduct);
            }
        });
        Purchase savedPurchase = purchaseRepository.savePurchase(purchase);
        assertEquals(purchase, savedPurchase);
    }

    @Test
    void findAllClientPurchasesTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        Client savedClient = clientRepository.saveClient(client);
        Product product = new Product("aaa", 1, 1, "aaa");
        Product savedProduct = productRepository.saveProduct(product);
        Purchase purchase = new Purchase(savedClient, new ArrayList<Product>() {
            {
                add(savedProduct);
            }
        });
        em.getTransaction().begin();
        Purchase savedPurchase = purchaseRepository.savePurchase(purchase);
        em.getTransaction().commit();
        assertEquals( new ArrayList<Purchase>() {
            {
                add(savedPurchase);
            }
        }, purchaseRepository.findAllClientPurchases(client));
    }

    @AfterAll
    static void endTest() {
        if (emf != null) emf.close();
    }
}