package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import p.lodz.Model.Client;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseRepositoryMongoDB implements PurchaseRepository {

    private final MongoCollection<Purchase> purchasesCollection;

    public PurchaseRepositoryMongoDB(MongoCollection<Purchase> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
    }


    @Override
    public Purchase findPurchaseById(UUID id) {
        return purchasesCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchasesCollection.find().into(new ArrayList<>());
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        purchasesCollection.insertOne(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> findAllClientPurchases(Client client) {
        return purchasesCollection.find(Filters.eq("client._id", client.getEntityId())).into(new ArrayList<>());
    }

}
