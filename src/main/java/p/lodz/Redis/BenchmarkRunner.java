package p.lodz.Redis;

import org.openjdk.jmh.annotations.*;
import p.lodz.Managers.ProductManager;
import p.lodz.Model.Product;
import p.lodz.Model.Shop;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkRunner {
    static Shop shop = new Shop();
    static ProductManager manager = new ProductManager(shop.getRepository().getDatabase().getCollection("product_benchmark_redis_test", Product.class));
    private boolean cacheClosed = false;
    @Setup
    public void setup() {
        for(int i =0;i < 100;i++){
            manager.registerProduct("redis_performance_test"+i, 50, 2, "benchmark");
        }
    }



    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void readFromCache() {
        manager.getProductCache().getProducts();
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void readFromMongo() {
        if (!cacheClosed) {
            manager.getProductCache().close();
            cacheClosed = true;
        }
        manager.getAllProducts();
    }
   @TearDown
   public void cleanup() {
       shop.close();
   }


    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
