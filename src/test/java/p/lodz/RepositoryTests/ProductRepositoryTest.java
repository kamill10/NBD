package p.lodz.RepositoryTests;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {
   /* static AbstractMongoRepository repository = new AbstractMongoRepository();

    static Shop shop = new Shop();
    static MongoDatabase productDatabase = shop.getRepository().getDatabase();

    static p.lodz.Repositiories.ProductRepository productRepository = new ProductRepository(productDatabase.getCollection("products", Product.class));


    @Test
    @Order(2)
    void saveProductTest() {
        Product product = new Product("buty", 1, 1, "aaa");
        assertEquals(product, productRepository.saveProduct(product));
    }

    @Test
    @Order(3)
    void archiveProductTest() {
        Product savedProduct = productRepository.saveProduct(new Product("skora", 1, 1, "aaa"));
       Product product1 = productRepository.archiveProduct(savedProduct.getEntityId(), true);
        assertTrue(product1.isArchived());
    }

    @Test
    @Order(4)
    void decrementNumberOfProductTest() {
        Product savedProduct = productRepository.saveProduct(new Product("koszulka", 1, 1, "aaa"));
        Product productAfterDecrement = productRepository.decrementNumberOfProducts(savedProduct.getEntityId(), 1);
        assertEquals(0, productAfterDecrement.getNumberOfProducts());
        assertThrows(MongoCommandException.class, () -> productRepository.decrementNumberOfProducts(savedProduct.getEntityId(), 2));
        assertEquals(0, productRepository.findProductById(savedProduct.getEntityId()).getNumberOfProducts());

    }

    @Test
    @Order(5)
    void findProductByIdTest() {
        Product savedProduct = productRepository.saveProduct(new Product("dres", 1, 1, "aaa"));
        assertEquals(savedProduct.getEntityId(), productRepository.findProductById(savedProduct.getEntityId()).getEntityId());
    }

    @Test
    @Order(1)
    void findAllProductsTest() {
        Product product = new Product("suknia", 1, 1, "aaa");
        Product product2 = new Product("talon", 1, 1, "aaa");
        productRepository.saveProduct(product);
        productRepository.saveProduct(product2);
        assertEquals(2, productRepository.findAllProducts().size());
    }

    @Test
    @Order(6)
    void deleteProduct() {
        Product product = new Product("aaa", 1, 1, "aaa");
        assertEquals(product, productRepository.saveProduct(product));
        assertTrue(productRepository.deleteProduct(product.getEntityId()));
        assertFalse(productRepository.deleteProduct(new ObjectId()));
    }

    @AfterAll
    static void cleanDataBase() {
        assertEquals(productRepository.findAllProducts().size(),6);
        //Document command = new Document("replSetStepDown", 60);
        //productDatabase.runCommand(command);
        assertEquals(productRepository.findAllProducts().size(),6);
        shop.close();
//        repository.close();

    } */




}