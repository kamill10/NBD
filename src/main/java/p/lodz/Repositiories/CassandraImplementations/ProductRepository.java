package p.lodz.Repositiories.CassandraImplementations;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import p.lodz.Model.Dao.ProductDao;
import p.lodz.Model.Mapper.ProductMapper;
import p.lodz.Model.Mapper.ProductMapperBuilder;
import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

public class ProductRepository implements p.lodz.Repositiories.ProductRepository {
    private static    CqlSession session;
    ProductMapper productMapper;
    ProductDao productDao;

    public static void prepareTables(){
        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("products"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("base_cost"),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("product_name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("discount"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("archived"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("number_of_products"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("description"), DataTypes.TEXT)
                .build());

    }

    public ProductRepository(CqlSession session) {
        ProductRepository.session = session;
        prepareTables();
        productMapper = new ProductMapperBuilder(session).build();
        productDao = productMapper.productDao();
    }

    @Override
    public void  saveProduct(Product product) {
         productDao.create(product);

    }



    @Override
    public void update(Product product) {
         productDao.update(product);
    }

    @Override
    public Product findProductById(UUID id) {
        return productDao.findProduct(id);
    }


    @Override
    public boolean  deleteProduct(Product product ) {
        return productDao.delete(product);
    }

    /*@Override
    public boolean archiveProduct(Product product, UUID id, boolean archived) {
        return  productDao.archiveProduct(product,id,archived);
    }

    @Override
    public boolean decrementProducts(Product product, UUID id, int numberOfProducts) {
        return productDao.decrementProducts(product,id,numberOfProducts);
    } */

}
