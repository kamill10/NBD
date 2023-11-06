package p.lodz.Managers;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import jakarta.persistence.LockModeType;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.InvalidDataException;
import p.lodz.Exceptions.InvalidPurchaseException;
import p.lodz.Model.*;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.Iterator;
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
    public Purchase registerPurchase(Client customer, ProductEntry product) {
        if (product == null) {
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        Purchase purchase = null;
        ClientSession session = repository.getClientSession();
        try(session) {
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY)
                    .readConcern(ReadConcern.MAJORITY)
                    .build());
            if(product.getProduct().getNumberOfProducts() < product.getQuantity() || product.getProduct().isArchived()){
                session.abortTransaction();
                return purchase;
            }
            try {
                productRepository.decrementNumberOfProducts(product.getProduct().getEntityId(), product.getQuantity());
                if(product.getProduct().getNumberOfProducts() == product.getQuantity()) {
                    productRepository.archiveProduct(product.getProduct().getEntityId(), true);
                }
            } catch (MongoCommandException e){
                session.abortTransaction();
                return purchase;
            }

            purchase = new Purchase(customer, product);
            purchase = purchaseRepository.savePurchase(purchase);
            session.commitTransaction();
        }catch (Exception e) {
            session.abortTransaction();
        }

        return purchase;
    }

    public Purchase registerPurchase(Client customer, List<ProductEntry> products) {
        if (products.isEmpty()) {
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        Purchase purchase = null;
        ClientSession session = repository.getClientSession();
        try(session) {
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY)
                    .readConcern(ReadConcern.MAJORITY)
                    .build());
            Iterator<ProductEntry> iterator = products.iterator();
            while (iterator.hasNext()) {
                ProductEntry product = iterator.next();
                if(product.getProduct().getNumberOfProducts() < product.getQuantity() || product.getProduct().isArchived()){
                    iterator.remove();
                    continue;
                }
                try {
                    productRepository.decrementNumberOfProducts(product.getProduct().getEntityId(), product.getQuantity());
                    if(product.getProduct().getNumberOfProducts() == product.getQuantity()) {
                        productRepository.archiveProduct(product.getProduct().getEntityId(), true);
                    }
                } catch (MongoCommandException e){
                    iterator.remove();
                }

            }
            if (products.isEmpty()){
                session.abortTransaction();
                return purchase;
            }

            purchase = new Purchase(customer, products);
            purchase = purchaseRepository.savePurchase(purchase);
            session.commitTransaction();
        }catch (Exception e) {

            session.abortTransaction();
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