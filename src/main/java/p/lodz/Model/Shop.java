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
import p.lodz.Repositiories.AbstractMongoRepository;

@Getter
public class Shop implements AutoCloseable{
    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final PurchaseManager purchaseManager;
    private final AbstractMongoRepository repository;

    public Shop() {
        repository = new AbstractMongoRepository();
        clientManager = new ClientManager(repository.getDatabase().getCollection("clients", Client.class));
        productManager = new ProductManager(repository.getDatabase().getCollection("products", Product.class));
        purchaseManager = new PurchaseManager(repository.getDatabase().getCollection("purchases", Purchase.class), repository.getDatabase().getCollection("product", Product.class), repository);
    }

    @Override
    public void close() throws Exception {
        repository.close();
    }
}
