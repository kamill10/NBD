package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository {
    List<Purchase> findByClientId(UUID id);
    List<Purchase> findByProductId(UUID id);
    Purchase  savePurchase(Purchase purchase);
    void endPurchase(Purchase purchase);



}
