package p.lodz.Managers;

import com.datastax.oss.driver.api.core.CqlSession;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.CassandraImplementations.PurchaseRepository;

import java.util.UUID;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;


    public PurchaseManager(CqlSession session) {
        this.purchaseRepository = new PurchaseRepository(session);
    }

    public Client getPurchaseByClientId(UUID id){
        return purchaseRepository.findByClientId(id);
    }
    public Product getPurchaseByProductId(UUID id){
        return purchaseRepository.findByProductId(id);
    }
    public void  registerPurchase(Client customer, Product product) {
        Purchase purchase = new Purchase(customer,product);
         purchaseRepository.savePurchase(purchase);
    }

    public void endPurchase(UUID id){
        purchaseRepository.endPurchase(id);
    }
}