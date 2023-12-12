package p.lodz.RepositoryTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import p.lodz.Model.Product;
import p.lodz.Repositiories.CassandraConfig;
import p.lodz.Repositiories.CassandraImplementations.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {

    static CassandraConfig config;
    static ProductRepository productRepository;

    @BeforeAll
    public static void setup() {
        config = new CassandraConfig();
        productRepository = new ProductRepository(config.getSession());
    }


    @Test
    public void testSaveProduct() {
        Product product = new Product("TestProductName", 100.0, 10, "TestDescription");
        productRepository.saveProduct(product);
        Product retrievedProduct = productRepository.findProductById(product.getId());
        assertNotNull(retrievedProduct);
        assertEquals(product.getProductName(), retrievedProduct.getProductName());
        assertEquals(product.getBaseCost(), retrievedProduct.getBaseCost());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("TestProductName", 100.0, 10, "TestDescription");
        productRepository.saveProduct(product);
        product.setProductName("UpdatedProductName");
        productRepository.update(product);
        Product updatedProduct = productRepository.findProductById(product.getId());
        assertNotNull(updatedProduct);
        assertEquals("UpdatedProductName", updatedProduct.getProductName());
    }

    @Test
    public void testFindProductById() {
        Product product = new Product("TestProductName", 100.0, 10, "TestDescription");
        productRepository.saveProduct(product);
        Product retrievedProduct = productRepository.findProductById(product.getId());
        assertNotNull(retrievedProduct);
        assertEquals(product.getProductName(), retrievedProduct.getProductName());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("TestProductName", 100.0, 10, "TestDescription");
        productRepository.saveProduct(product);
        boolean deletionResult = productRepository.deleteProduct(product);
        assertTrue(deletionResult);
        Product deletedProduct = productRepository.findProductById(product.getId());
        assertNull(deletedProduct);
    }
}
