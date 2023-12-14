package p.lodz.Managers;

import com.datastax.oss.driver.api.core.CqlSession;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.CassandraImplementations.PurchaseRepository;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;
import java.util.UUID;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;


    public PurchaseManager(CqlSession session, p.lodz.Repositiories.CassandraImplementations.ProductRepository productRepository) {
        this.purchaseRepository = new PurchaseRepository(session,productRepository);
        this.productRepository = productRepository;
    }

    public List<Purchase> getPurchaseByClientId(UUID id){
        return purchaseRepository.findByClientId(id);
    }
    public List<Purchase> getPurchaseByProductId(UUID id){
        return purchaseRepository.findByProductId(id);
    }
    public Purchase registerPurchase(Client customer, Product product) {
        if(productRepository.findProductById(product.getId()).getNumberOfProducts() <= 0){
            throw new RuntimeException("Za malo dostepnych produktow");
        }
        else {
            product.setNumberOfProducts(product.getNumberOfProducts()-1);
            productRepository.update(product);
        }
        Purchase purchase = new Purchase(customer,product);
          return purchaseRepository.savePurchase(purchase);
    }

    public void endPurchase(Purchase  rent ){
        purchaseRepository.endPurchase(rent );
    }
}