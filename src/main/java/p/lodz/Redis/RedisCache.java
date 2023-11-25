package p.lodz.Redis;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.bson.types.ObjectId;
import p.lodz.Exceptions.ExceptionRedis;
import p.lodz.Model.Product;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RedisCache {
    JedisPool pool = new RedisConfig().getJedisPool();
    Jsonb jsonb = JsonbBuilder.create();
    public void saveProduct(Product product) {
        try (Jedis jedis = pool.getResource()) {
            String productJson = jsonb.toJson(product);
            String cacheKey = "product:"+ product.getEntityId();
            jedis.setex(cacheKey,100,productJson);
        }  catch (JedisException e) {
            throw new ExceptionRedis("Error while saving   product in Redis.");
        }
    }
    public Product getProductData(ObjectId id){
        try(Jedis jedis = pool.getResource()){
            String cacheKey = "product:"+ id;
            String product = jedis.get(cacheKey);
            if (product != null) {
                return jsonb.fromJson(product, Product.class);
            } else {
                // Throw an exception when no product is found
                throw new ExceptionRedis("No product found for id: " + id.toString());
            }
        }
        catch (JedisException e) {
            throw new ExceptionRedis("Error while geting product in Redis.");
        }
    }
    public List<Product>getProducts(){
        List<Product>products = new ArrayList<>();
        try(Jedis jedis = new Jedis()){
            Set<String> productKeys = jedis.keys("product:*");
            if (productKeys.isEmpty()) {
                // Throw an exception when the list is empty
                throw new ExceptionRedis("No products found in Redis.");
            }
            for(String key:productKeys){
                String productJson = jedis.get(key);
                products.add(jsonb.fromJson(productJson,Product.class));
            }
        }
        catch (JedisException e) {
            throw new ExceptionRedis("Error while geting list of  products in Redis.");
        }
        return products;
    }
    public void deleteProduct(ObjectId id){
        try(Jedis jedis = pool.getResource()){
            String cacheKey = "product:"+id;
            if(jedis.get(cacheKey) == null) {
                throw new ExceptionRedis("No product found for id: " + id.toString());
            }
            else{
                jedis.del(cacheKey);
            }
        }
        catch (JedisException e) {
            throw new ExceptionRedis("Error while deleting product in Redis.");
        }
    }
    public void clearCache() throws ExceptionRedis {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushAll();
        } catch (JedisException e) {
            throw new ExceptionRedis("Error while clearing cache in Redis.");
        }
    }

    public void clearOne(ObjectId id) throws ExceptionRedis {
        try (Jedis jedis = pool.getResource()) {
            String cacheKey = "product:" + id;
            jedis.del(cacheKey);
        } catch (JedisException e) {
            throw new ExceptionRedis("Error while clearing cache for room in Redis.");
        }
    }
}