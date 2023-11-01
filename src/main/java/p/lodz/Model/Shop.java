package p.lodz.Model;

import jakarta.persistence.EntityManager;
import jakarta.validation.Validator;
import p.lodz.Managers.ClientManager;
import p.lodz.Managers.ProductManager;
import p.lodz.Managers.PurchaseManager;

public class Shop {
    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final PurchaseManager purchaseManager;

    public Shop(EntityManager em, Validator validator) {
        clientManager = new ClientManager(em, validator);
        productManager = new ProductManager(em, validator);
        purchaseManager = new PurchaseManager(em);
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public PurchaseManager getPurchaseManager() {
        return purchaseManager;
    }
}
