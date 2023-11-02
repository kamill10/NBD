package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import p.lodz.Model.Client;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.List;

public class PurchaseRespositoryMongoDB implements PurchaseRepository {


    private MongoCollection<Purchase> purchasesCollection;

    public PurchaseRespositoryMongoDB(MongoCollection<Purchase> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
    }


    @Override
    public Purchase findPurchaseById(Long id) {
        return null;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return null;
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        purchasesCollection.insertOne(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> findAllClientPurchases(Client client) {
        return null;
    }
}
