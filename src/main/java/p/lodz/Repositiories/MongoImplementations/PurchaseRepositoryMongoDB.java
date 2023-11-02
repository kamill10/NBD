package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepositoryMongoDB implements PurchaseRepository {

    private final MongoCollection<Purchase> purchasesCollection;

    public PurchaseRepositoryMongoDB(MongoCollection<Purchase> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
    }


    @Override
    public Purchase findPurchaseById(ObjectId id) {
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
        return purchasesCollection.find(Filters.eq("client", client)).into(new ArrayList<>());
    }

}
