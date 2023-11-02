package p.lodz.Managers;

import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.InvalidPurchaseException;
import p.lodz.Model.*;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    public PurchaseManager(MongoCollection<Purchase> purchaseCollection) {
        this.purchaseRepository = new PurchaseRepositoryMongoDB(purchaseCollection);
    }

    public Purchase getPurchase(ObjectId id){
        return purchaseRepository.findPurchaseById(id);
    }

    public Purchase registerPurchase(Client customer, Map<Product, Integer> products) {
        if (products.isEmpty()) {
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        Purchase purchase = new Purchase(customer, products);
        purchase = purchaseRepository.savePurchase(purchase);
        return purchase;
    }

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }

    public List<Purchase> getAllClientPurchases(Client client) {
        return purchaseRepository.findAllClientPurchases(client);
    }
}