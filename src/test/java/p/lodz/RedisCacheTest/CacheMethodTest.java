package p.lodz.RedisCacheTest;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.*;
import p.lodz.Exceptions.ProductException;
import p.lodz.Managers.ProductManager;
import p.lodz.Model.Product;
import p.lodz.Model.Shop;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CacheMethodTest {
    static Shop shop = new Shop();
    static MongoDatabase productDatabase = shop.getRepository().getDatabase();
    static ProductManager manager = new ProductManager(productDatabase.getCollection("product_redis_test", Product.class));
    static ProductRepository repo = new ProductRepositoryMongoDB(productDatabase.getCollection("product_redis_test", Product.class)) ;



    @Order(1)
    @Test
    public void addToCacheTest(){
        Product product = manager.registerProduct("testowy,",20,10,"reedis_test");
        System.out.println("xdd+"+product.getEntityId());
        System.out.println("xdd+"+manager.getProductCache().getProductData(product.getEntityId()).getEntityId());
        assertEquals(manager.getProductCache().getProducts().size(),1);
        assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getNumberOfProducts(),product.getNumberOfProducts());
        assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getProductName(),product.getProductName());
        assertEquals(manager.getProductCache().getProductData(product.getEntityId()).isArchived(),product.isArchived());

    }
    @Order(2)
    @Test
    public void getProductFromCacheTest() {
        Product product = manager.registerProduct("test_product", 30, 5, "redis_test_get");
        Product cachedProduct = manager.getProduct(product.getEntityId());

        assertEquals(cachedProduct.getProductName(), product.getProductName());
        assertEquals(cachedProduct.getBaseCost(), product.getBaseCost());
        assertEquals(cachedProduct.getNumberOfProducts(), product.getNumberOfProducts());
        assertEquals(cachedProduct.getDescription(), product.getDescription());
    }

    @Order(3)
    @Test
    public void getAllProductsTest() {
        Product product = manager.registerProduct("test_product_2", 40, 3, "redis_test_all");

        List<Product> allProducts = manager.getAllProducts();
        assertEquals(allProducts.size(), manager.getProductCache().getProducts().size());
        System.out.println(manager.getProductCache().getProducts());
            assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getProductName(), product.getProductName());
            assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getBaseCost(), product.getBaseCost());
            assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getNumberOfProducts(), product.getNumberOfProducts());
            assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getDescription(), product.getDescription());

    }
    @Order(4)
    @Test
    public void clearCacheAndUpdateCache(){
        Product product = manager.registerProduct("test_product_2", 40, 3, "redis_test_all");
        int sizeBeforeClear =  manager.getAllProducts().size();
        manager.getProductCache().clearCache();
        //cache nie posiada zadnych produktow
        assertEquals(manager.getProductCache().getProducts().size(),0);
        //symulacja przy pobieraniu produktor cache ma inna zawartosc niz baza danych/wykoany zostanie clear cache
        manager.getAllProducts();
        assertEquals(manager.getProductCache().getProducts().size(),sizeBeforeClear);
        assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getProductName(),product.getProductName());
        //update cacha do stanu bazy
    }
    @Order(5)
    @Test
    public void decrementNumberOfProductTest(){
        Product product = manager.registerProduct("test_product_2", 40, 3, "redis_test_all");
        repo.decrementNumberOfProducts(product.getEntityId(),1);
        assertEquals(manager.getProductCache().getProductData(product.getEntityId()).getNumberOfProducts(),2);

    }

    @Order(6)
    @Test
    public void deleteProductTest() {
        Product productToDelete = manager.registerProduct("product_to_delete", 50, 2, "redis_test_delete");
        manager.deleteProduct(productToDelete.getEntityId());

        assertThrows(ProductException.class, () -> manager.getProductCache().getProductData(productToDelete.getEntityId()),
                "Expected getProductCache().getProductData() to throw ProductException after deletion");

    }
    @Order(7)
    @Test
    public void loseConnectionWithRedis(){
        Product product = manager.registerProduct("redis_lost_connection", 50, 2, "redis_test_delete");
        manager.getProductCache().clearCache();
        manager.getProductCache().close();
        assertEquals(manager.getProduct(product.getEntityId()).getProductName(), product.getProductName());
        assertEquals(manager.getProduct(product.getEntityId()).getDescription(), product.getDescription());
        assertEquals(manager.getProduct(product.getEntityId()).getBaseCost(), product.getBaseCost());
    }
    @AfterAll
    static void cleanDataBase() {
        shop.close();

    }
}
