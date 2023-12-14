package p.lodz;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import jnr.constants.platform.Local;
import p.lodz.Managers.ClientManager;
import p.lodz.Managers.ProductManager;
import p.lodz.Managers.PurchaseManager;
import p.lodz.Model.*;
import p.lodz.Model.Provider.ClientTypeRepo;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.CassandraConfig;
import p.lodz.Repositiories.CassandraImplementations.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        try(CassandraConfig cassandraConfig = new CassandraConfig()){
            ProductManager productManager = new ProductManager(cassandraConfig.getSession());
            ClientManager clientManager = new ClientManager(cassandraConfig.getSession());
            Product product = new Product("Zmniejszy",69,10,"sportowe");
            productManager.registerProduct(product);
            product.setNumberOfProducts(9);
            productManager.updateProduct(product);
            System.out.println(productManager.getProduct(product.getId()).toString());
            Client client2 = new Client("Mateusz","Smoła","premium",new Address("d","s","12"));
            clientManager.registerClient(client2);
            System.out.println(clientManager.getClient(client2.getId()).toString());

            PurchaseManager purchaseManager = new PurchaseManager(cassandraConfig.getSession(), (ProductRepository) productManager.getProductRepository());
            Purchase purchase = purchaseManager.registerPurchase(client2,product);
            System.out.println(purchaseManager.getPurchaseByClientId(client2.getId()));
            System.out.println(purchaseManager.getPurchaseByProductId(product.getId()));
            purchaseManager.endPurchase(purchase);
        }


    }
}
