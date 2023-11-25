package p.lodz.Managers;


import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.ExceptionRedis;
import p.lodz.Exceptions.ProductException;
import p.lodz.Model.Product;
import p.lodz.Redis.RedisProductCache;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

public class ProductManager {
    private final ProductRepository productRepository;
    private RedisProductCache productCache = new RedisProductCache();

    public ProductManager(MongoCollection<Product> collection) {
        this.productRepository = new ProductRepositoryMongoDB(collection);
    }

    public Product getProduct(ObjectId id) {
        try {
            return productCache.getProductData(id);
        } catch (ExceptionRedis e) {
            return productRepository.findProductById(id);
        } catch (ProductException e) {
            if (productRepository.findProductById(id) == null) {
                throw new ProductException("No product with id in database");
            } else {
                Product product = productRepository.findProductById(id);
                productCache.saveProduct(new Product(id, productRepository.findProductById(id).getProductName(),
                        productRepository.findProductById(id).getBaseCost(),
                        productRepository.findProductById(id).getNumberOfProducts(),
                        productRepository.findProductById(id).getDescription()));
                return product;
            }
        }
    }

    public Product registerProduct(String productName, double baseCost, int numberOfProducts, String description) {
        Product product = new Product(productName, baseCost, numberOfProducts, description);
        Product savedProduct = productRepository.saveProduct(product);
        try {
            productCache.saveProduct(savedProduct);
        } catch (ExceptionRedis e) {
            return savedProduct;
        }
        return savedProduct;
    }


    public List<Product> getAllProducts() {
        List<Product>products;
        if(productRepository.findAllProducts().size() == productCache.getProducts().size()){
            try{
                return productCache.getProducts();
            }
            catch (ExceptionRedis e){
                return productRepository.findAllProducts();
            }
        }
        else{
             productCache.clearCache();
             productCache.updateListOfProduct(productRepository.findAllProducts());
        }
        return productCache.getProducts();
    }

    public boolean deleteProduct(ObjectId id ){
        try{
            productCache.deleteProduct(id);
        }
        catch(ExceptionRedis e){
            return productRepository.deleteProduct(id);
        }
        productCache.deleteProduct(id);
        return productRepository.deleteProduct(id);
    }


}
