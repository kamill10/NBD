package p.lodz;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import p.lodz.Model.*;
import p.lodz.Model.Type.Standard;

public class App {
    public static void main(String[] args) {


        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
            EntityManager em = emf.createEntityManager();
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Shop shop = new Shop(em, validator);
            shop.getClientManager().registerClient("null", "bbb", "wwa", "ullica", "10", new Standard());
            shop.getProductManager().registerProduct("aaa", 10, 1, "aabb");
            shop.getClientManager().registerClient("aaa", "bbbb", "wwa", "ullica", "10", new Standard());
            shop.getPurchaseManager().registerPurchase(shop.getClientManager().getClient(1L), shop.getProductManager().getAllProducts());
            shop.getProductManager().registerProduct("bbb", 10, 1, "ccdd");
            shop.getClientManager().registerClient("bbb", "cccc", "wwa", "ullica", "10", new Standard());
            shop.getPurchaseManager().registerPurchase(shop.getClientManager().getClient(2L), shop.getProductManager().getProduct(2L));
        }
    }
}
