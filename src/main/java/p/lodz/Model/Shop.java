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
    private final ConnectionString connectionString = new ConnectionString("mongodb://admin:adminpassword@localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set_single");
    private final MongoClient mongoClient = MongoClients.create(connectionString);
    private final MongoDatabase mongoDatabase = mongoClient.getDatabase("nbddb");

    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final PurchaseManager purchaseManager;

    public Shop(EntityManager em, Validator validator) {
        clientManager = new ClientManager(em, validator);
        productManager = new ProductManager(em, validator);
        purchaseManager = new PurchaseManager(em);
    }

}
