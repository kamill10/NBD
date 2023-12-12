package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository {
    Client findByClientId(UUID id);
    Product findByProductId(UUID id);
    void  savePurchase(Purchase purchase);
    void endPurchase(UUID id);


}
