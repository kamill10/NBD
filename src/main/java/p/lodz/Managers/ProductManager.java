package p.lodz.Managers;


import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.RedisException;
import p.lodz.Exceptions.ProductException;
import p.lodz.Model.Product;
import p.lodz.Redis.RedisProductCache;
import p.lodz.Repositiories.MongoImplementations.ProductRepositoryMongoDB;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

@Getter
public class ProductManager {
    private final ProductRepository productRepository;
    private final RedisProductCache productCache = new RedisProductCache();

    public ProductManager(MongoCollection<Product> collection) {
        this.productRepository = new ProductRepositoryMongoDB(collection);
    }

    public Product getProduct(ObjectId id) {
        Product product;
        try {
            product = productCache.getProductData(id);
        } catch (RedisException e) {
            return productRepository.findProductById(id);
        } catch (ProductException e) {
            Product repositoryProduct = productRepository.findProductById(id);
            if (repositoryProduct == null) {
                throw new ProductException("No product with id in database");
            } else {
                productCache.saveProduct(new Product(id, repositoryProduct.getProductName(),
                        repositoryProduct.getBaseCost(),
                        repositoryProduct.getNumberOfProducts(),
                        repositoryProduct.getDescription()));
                return repositoryProduct;
            }
        }
        return product;
    }

    public Product registerProduct(String productName, double baseCost, int numberOfProducts, String description) {
        Product product = new Product(productName, baseCost, numberOfProducts, description);
        Product savedProduct = productRepository.saveProduct(product);
        try {
            productCache.saveProduct(savedProduct);
        } catch (RedisException e) {
            return savedProduct;
        }
        return savedProduct;
    }


    public List<Product> getAllProducts() {
        List<Product>products;
        try {
            productCache.getProducts();
            if (productRepository.findAllProducts().size() == productCache.getProducts().size()) {
                products =  productCache.getProducts();
            } else {
                productCache.clearCache();
                productCache.updateListOfProduct(productRepository.findAllProducts());
                products =  productCache.getProducts();
            }
        } catch (RedisException e) {
            return productRepository.findAllProducts();
        }
        return products;
    }

    public boolean deleteProduct(ObjectId id ){
        try{
            productCache.deleteProduct(id);
        }
        catch(RedisException e){
            return productRepository.deleteProduct(id);
        }
        return productRepository.deleteProduct(id);
    }


}
