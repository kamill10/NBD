package p.lodz.Redis;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.RedisException;
import p.lodz.Exceptions.ProductException;
import p.lodz.Model.Product;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisProductCache {
    JedisPool pool = new RedisConfig().getJedisPool();
    Jsonb jsonb = JsonbBuilder.create();
    public void saveProduct(Product product) {
        try (Jedis jedis = pool.getResource()) {
            String productJson = jsonb.toJson(product);
            String cacheKey = "product:"+ product.getEntityId();
            jedis.setex(cacheKey,100,productJson);
        }  catch (JedisException e) {
            throw new RedisException("Error while saving   product in Redis.");
        }
    }
    public void updateListOfProduct(List<Product>products){
        try (Jedis jedis = pool.getResource()) {
            for (Product product : products) {
                String cacheKey = "product:" + product.getEntityId();
                String productJson = jsonb.toJson(product);
                jedis.setex(cacheKey, 100, productJson);
            }
        } catch (JedisException e) {
            throw new RedisException("Error while updating list of products in Redis.");
        }
    }
    public void archiveProduct(ObjectId id){
        try(Jedis jedis = pool.getResource()){
            String key = "product:"+id;
            if(jedis.exists(key)){
                Product product = jsonb.fromJson(jedis.get(key),Product.class);
                product.setArchived(true);
                String  updatedProduct = jsonb.toJson(product);
                jedis.set(key,updatedProduct);
            }
            else{
                throw new ProductException("There is no product with this id");
            }
        }
        catch (JedisException e) {
            throw new RedisException("Error while archiving  product in Redis.");
        }
    }
    public void decrementProduct(ObjectId id,int qantity){
        try(Jedis jedis = pool.getResource()){
            String key = "product:"+id;
            if(jedis.exists(key)){
                Product product = jsonb.fromJson(jedis.get(key),Product.class);
                product.setNumberOfProducts(product.getNumberOfProducts()-qantity);
                String  updatedProduct = jsonb.toJson(product);
                jedis.set(key,updatedProduct);
            }
            else{
                throw new ProductException("There is no product with this id");
            }
        }
        catch (JedisException e) {
            throw new RedisException("Error while decrement number of   product in Redis.");
        }
    }
    public Product getProductData(ObjectId id){
        try(Jedis jedis = pool.getResource()){
            String cacheKey = "product:"+ id;
            String product = jedis.get(cacheKey);
            if (product != null) {
                return jsonb.fromJson(product, Product.class);
            } else {
                throw new ProductException("No product found for id: " + id.toString());
            }
        }
        catch (JedisException e) {
            throw new RedisException("Error while geting product in Redis."+e);
        }
    }
    public List<Product>getProducts(){
            List<Product> products = new ArrayList<>();
            try (Jedis jedis = pool.getResource()) {
                Set<String> productKeys = jedis.keys("product:*");
                if (!productKeys.isEmpty()) {
                    for (String key : productKeys) {
                        String productJson = jedis.get(key);
                        products.add(jsonb.fromJson(productJson, Product.class));
                    }
                }
            } catch (JedisException e) {
                throw new RedisException("Error while getting list of products in Redis." +e);
            }
            return products;
        }
    public void deleteProduct(ObjectId id){
        try(Jedis jedis = pool.getResource()){
            String cacheKey = "product:"+id;
            if(jedis.get(cacheKey) == null) {
                throw new ProductException("No product found for id: " + id.toString());
            }
            else{
                jedis.del(cacheKey);
            }
        }
        catch (JedisException e) {
            throw new RedisException("Error while deleting product in Redis.");
        }
    }
    public void clearCache() throws RedisException {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushAll();
        } catch (JedisException e) {
            throw new RedisException("Error while clearing cache in Redis."+e.getMessage());
        }
    }
    public void close(){
        clearCache();
        pool.close();
    }
}