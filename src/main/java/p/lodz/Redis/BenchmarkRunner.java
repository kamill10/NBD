package p.lodz.Redis;

import org.bson.types.ObjectId;
import org.openjdk.jmh.annotations.*;
import p.lodz.Managers.ProductManager;
import p.lodz.Model.Product;
import p.lodz.Model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkRunner {
    private static final int NUM_PRODUCTS = 1000;
    private static final String collectionName = "product_benchmark_redis_test";

    private static final Shop shop = new Shop();
    private static final ProductManager manager = new ProductManager(shop.getRepository().getDatabase().getCollection(collectionName, Product.class));

    private final List<ObjectId> ids = new ArrayList<>();
    private boolean cacheClosed = false;

    @Setup
    public void setup() {
        for (int i = 0; i < NUM_PRODUCTS; i++) {
            Product product = manager.registerProduct("redis_performance_test" + i, 50, 2, "benchmark");
            ids.add(product.getEntityId());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void readFromCache() {
        for (ObjectId objectId : ids) {
            manager.getProduct(objectId);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void readFromMongo() {
        if (!cacheClosed) {
            manager.getProductCache().close();
            cacheClosed = true;
        }
        for (ObjectId objectId : ids) {
            manager.getProduct(objectId);
        }
    }

    @TearDown
    public void cleanup() {
        shop.close();
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
