package p.lodz;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
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
        try(Shop shop = new Shop();) {
//            Product product = new Product("bbb", 10, 1, "aabb");
//
//            Product product1 = new Product("test", 120, 3, "casf");
            Product product = shop.getProductManager().registerProduct("bbb", 10, 1, "aabb");
            Product product1 = shop.getProductManager().registerProduct("test", 120, 3, "casf");
            ClientType type1 = new Standard();
            ClientType type2 = new Premium();
            ClientType type3 = new PremiumDeluxe();
            Client client = shop.getClientManager().registerClient(
                    "Konrad1", "koza1", "Lodz1", "przykladow2a", "44A", type1);
            Client client2 = shop.getClientManager().registerClient(
                    "Konrad1", "koza1", "Lodz1", "przykladow2a", "44A", type2);


            List<ProductEntry> purchases = new ArrayList<>();
            purchases.add(new ProductEntry(product, 1));
            purchases.add(new ProductEntry(product1, 3));
//            Purchase purchase1 = new Purchase(client, purchases);
            Purchase purchase1 = shop.getPurchaseManager().registerPurchase(client, purchases);
            shop.getRepository().getDatabase().getCollection("clients").find().forEach(System.out::println);
            System.out.println(client2);
            Client clnt = shop.getRepository().getDatabase().getCollection("clients", Client.class).find(Filters.eq("_id", client2.getEntityId())).first();
            System.out.println(clnt);
            shop.getRepository().getDatabase().getCollection("products").find().forEach(System.out::println);
            shop.getRepository().getDatabase().getCollection("purchases").find().forEach(System.out::println);
//            System.out.println(clientRepository.findClientById(client2.getEntityId()));
        } catch (Exception e){
            e.printStackTrace();
//            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
