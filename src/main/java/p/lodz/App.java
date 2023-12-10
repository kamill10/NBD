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

public class App {
    public static void main(String[] args) {
        CassandraConfig cassandraConfig = new CassandraConfig();
        ProductManager productManager = new ProductManager(cassandraConfig.getSession());
        productManager.registerProduct("buty",30,10,"zimowe");
    }
}
