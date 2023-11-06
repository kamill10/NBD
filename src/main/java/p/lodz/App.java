package p.lodz;

import com.mongodb.client.model.Filters;
import p.lodz.Model.*;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try(Shop shop = new Shop()) {
            Product product = shop.getProductManager().registerProduct("bbb", 10, 1, "aabb");
            Product product1 = shop.getProductManager().registerProduct("test", 120, 3, "casf");
            ClientType type1 = new Standard();
            ClientType type2 = new Premium();
            ClientType type3 = new PremiumDeluxe();
            Client client = shop.getClientManager().registerClient(
                    "Konrad1", "koza1", "Lodz1", "przykladow2a", "44A", type1);
            Client client2 = shop.getClientManager().registerClient(
                    "Konrad1", "koza1", "Lodz1", "przykladow2a", "44A", type2);

            ProductRepositoryMongoDB testRepo = new ProductRepositoryMongoDB(shop.getRepository().getDatabase().getCollection("products", Product.class));
            Product productTest = shop.getProductManager().registerProduct("NegativeTest", 120, 1, "casf");
            try {
                testRepo.decrementNumberOfProducts(productTest.getEntityId(), 2);
                System.out.println(testRepo.findProductById(productTest.getEntityId()).toString());
            }catch (Exception e){
                System.out.println(e.toString());
                System.out.println(testRepo.findProductById(productTest.getEntityId()));
            }

            List<ProductEntry> purchases = new ArrayList<>();
            purchases.add(new ProductEntry(product, 1));
            purchases.add(new ProductEntry(product1, 5));
            Purchase purchase1 = shop.getPurchaseManager().registerPurchase(client, purchases);
//            shop.getRepository().getDatabase().getCollection("clients").find().forEach(System.out::println);
//            System.out.println(client2);
            Client clnt = shop.getRepository().getDatabase().getCollection("clients", Client.class).find(Filters.eq("_id", client2.getEntityId())).first();
//            System.out.println(clnt);
            shop.getRepository().getDatabase().getCollection("products").find().forEach(System.out::println);
//            shop.getRepository().getDatabase().getCollection("purchases").find().forEach(System.out::println);
//            System.out.println(clientRepository.findClientById(client2.getEntityId()));
            shop.getRepository().getDatabase().getCollection("purchases").find().forEach(System.out::println);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
