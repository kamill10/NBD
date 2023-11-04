package p.lodz.Managers;

import com.mongodb.MongoCommandException;
import com.mongodb.ReadConcern;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.InvalidPurchaseException;
import p.lodz.Model.*;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final AbstractMongoRepository repository;

    public PurchaseManager(MongoCollection<Purchase> purchaseCollection,
                           MongoCollection<Product> productCollection, AbstractMongoRepository repository) {
        this.purchaseRepository = new PurchaseRepositoryMongoDB(purchaseCollection);
        this.productRepository = new ProductRepositoryMongoDB(productCollection);
        this.repository = repository;
    }

    public Purchase getPurchase(ObjectId id){
        return purchaseRepository.findPurchaseById(id);
    }

    public Purchase registerPurchase(Client customer, List<ProductEntry> products) {
        if (products.isEmpty()) {
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        Purchase purchase = null;
        ClientSession session = repository.getClientSession();
        try {
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY)
                    .readConcern(ReadConcern.MAJORITY)
                    .build());
            products.forEach(e -> {
                productRepository.decrementNumberOfProducts(e.getProduct().getEntityId(), e.getQuantity());
            });
            purchase = new Purchase(customer, products);
            purchase = purchaseRepository.savePurchase(purchase);
            session.commitTransaction();
        }catch (MongoCommandException e) {
            session.abortTransaction();
        } finally {
            session.close();

        }

        return purchase;
    }

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }

    public List<Purchase> getAllClientPurchases(Client client) {
        return purchaseRepository.findAllClientPurchases(client);
    }
}