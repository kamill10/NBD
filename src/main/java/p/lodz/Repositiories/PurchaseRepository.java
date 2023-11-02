package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase findPurchaseById(ObjectId id);
    List<Purchase> findAllPurchases();
    Purchase savePurchase(Purchase purchase);
    List<Purchase> findAllClientPurchases(Client client);

}
