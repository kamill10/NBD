package p.lodz.Model;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.validation.Validator;
import lombok.Getter;
import p.lodz.Managers.ClientManager;
import p.lodz.Managers.ProductManager;
import p.lodz.Managers.PurchaseManager;

@Getter
public class Shop {
    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final PurchaseManager purchaseManager;

    public Shop(MongoDatabase database) {
        clientManager = new ClientManager(database.getCollection("client", Client.class));
        productManager = new ProductManager(database.getCollection("product", Product.class));
        purchaseManager = new PurchaseManager(database.getCollection("purchase", Purchase.class));
    }

}
