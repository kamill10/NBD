package p.lodz.Managers;

import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.InvalidPurchaseException;
import p.lodz.Model.*;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseManager(MongoCollection<Purchase> purchaseCollection,
                           MongoCollection<Product> productCollection) {
        this.purchaseRepository = new PurchaseRepositoryMongoDB(purchaseCollection);
        this.productRepository = new ProductRepositoryMongoDB(productCollection);
    }

    public Purchase getPurchase(ObjectId id){
        return purchaseRepository.findPurchaseById(id);
    }

    public Purchase registerPurchase(Client customer, List<ProductEntry> products) {
        if (products.isEmpty()) {
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        products.forEach(e -> {
            productRepository.decrementNumberOfProducts(e.getProduct().getEntityId(), e.getQuantity());
        });
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