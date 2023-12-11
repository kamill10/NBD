package p.lodz;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import p.lodz.Managers.ProductManager;
import p.lodz.Model.*;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.CassandraConfig;
import p.lodz.Repositiories.CassandraImplementations.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        CassandraConfig cassandraConfig = new CassandraConfig();
        ProductManager productManager = new ProductManager(cassandraConfig.getSession());
        Product product = new Product("test",69,10,"sportowe");
        System.out.println(productManager.getProduct(product.getId()).toString());
    }
}
