package p.lodz;

import com.mongodb.client.MongoDatabase;
import p.lodz.Model.*;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.PurchaseRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.List;

public class App {
    public static void main(String[] args) {
        try(AbstractMongoRepository repository = new AbstractMongoRepository();) {
            final MongoDatabase mongoDatabase = repository.getDatabase();
            ProductRepository  productRepository = new ProductRepositoryMongoDB(mongoDatabase.getCollection("products", Product.class));
            PurchaseRepository purchaseRepository = new PurchaseRepositoryMongoDB(mongoDatabase.getCollection("purchases", Purchase.class));
            Product product = new Product("bbb", 10, 1, "aabb");
            productRepository.saveProduct(product);
            Product product1 = new Product("test", 120, 3, "casf");
            Client client = new Client(
                    "Konrad1", "kozaaa1", new Address("Lodz1", "przykladow2a", "44A"), new Standard());
            Client client2 = new Client(
                    "Konrad1", "koza1", new Address("Lodz1", "przykladow2a", "44A"), new Premium());
            ClientRepository clientRepository = new ClientRepositoryMongoDB(mongoDatabase.getCollection("clients", Client.class));
            clientRepository.saveClient(client);
            clientRepository.saveClient(client2);
            Purchase purchase1 = new Purchase(client, List.of(product, product1));
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
