package p.lodz.Repositiories.CassandraImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepository implements p.lodz.Repositiories.PurchaseRepository {



    public PurchaseRepository() {

    }


    @Override
    public Purchase findPurchaseById(ObjectId id) {
       return null;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return null;
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return null;
    }

    @Override
    public List<Purchase> findAllClientPurchases(Client client) {
        return null;
    }

}
