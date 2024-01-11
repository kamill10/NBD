package p.lodz.Repositiories;

import p.lodz.Model.Client;
import p.lodz.Model.Purchase;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository {
    Purchase findPurchaseById(UUID id);
    List<Purchase> findAllPurchases();
    Purchase savePurchase(Purchase purchase);
    List<Purchase> findAllClientPurchases(Client client);

}
