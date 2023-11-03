package p.lodz;

import com.mongodb.client.MongoDatabase;
import p.lodz.Model.*;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.*;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ClientTypeRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try(AbstractMongoRepository repository = new AbstractMongoRepository();) {
            final MongoDatabase mongoDatabase = repository.getDatabase();
            ProductRepository  productRepository = new ProductRepositoryMongoDB(mongoDatabase.getCollection("products", Product.class));
            PurchaseRepository purchaseRepository = new PurchaseRepositoryMongoDB(mongoDatabase.getCollection("purchases", Purchase.class));
            Product product = new Product("bbb", 10, 1, "aabb");
            productRepository.saveProduct(product);
            Product product1 = new Product("test", 120, 3, "casf");
            ClientType type1 = new Standard();
            ClientType type2 = new Premium();
            ClientType type3 = new PremiumDeluxe();
            ClientTypeRepository clientTypeRepository = new ClientTypeRepositoryMongoDB(mongoDatabase.getCollection("types", ClientType.class));
            clientTypeRepository.saveClientType(type1);
            clientTypeRepository.saveClientType(type2);
            clientTypeRepository.saveClientType(type3);
            Client client = new Client(
                    "Konrad1", "koza1", new Address("Lodz1", "przykladow2a", "44A"), clientTypeRepository.getClientType("standard"));
            Client client2 = new Client(
                    "Konrad1", "koza1", new Address("Lodz1", "przykladow2a", "44A"), clientTypeRepository.getClientType("premium"));
            ClientRepository clientRepository = new ClientRepositoryMongoDB(mongoDatabase.getCollection("clients", Client.class));
            clientRepository.saveClient(client);
            clientRepository.saveClient(client2);
            List<ProductEntry> purchases = new ArrayList<>();
            purchases.add(new ProductEntry(product, 1));
            purchases.add(new ProductEntry(product1, 3));
            Purchase purchase1 = new Purchase(client, purchases);
            purchaseRepository.savePurchase(purchase1);
            repository.getDatabase().getCollection("clients").find().forEach(System.out::println);
            repository.getDatabase().getCollection("products").find().forEach(System.out::println);
            repository.getDatabase().getCollection("purchases").find().forEach(System.out::println);
        } catch (Exception e){
            e.printStackTrace();
//            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
