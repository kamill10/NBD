package p.lodz.Repositiories;

import p.lodz.Model.Client;
import p.lodz.Model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase findPurchaseById(Long id);
    List<Purchase> findAllPurchases();
    Purchase savePurchase(Purchase purchase);
    List<Purchase> findAllClientPurchases(Client client);

}
