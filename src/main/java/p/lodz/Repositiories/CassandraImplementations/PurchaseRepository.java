package p.lodz.Repositiories.CassandraImplementations;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class PurchaseRepository implements p.lodz.Repositiories.PurchaseRepository {

    CqlSession session;
    ProductRepository productRepository;

    public PurchaseRepository(CqlSession session, ProductRepository productRepository) {
        this.session = session;
        this.productRepository = productRepository;
        createTable();
    }

    void createTable(){
        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("rents_by_client"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("purchase_date"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("id"),DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("delivery_data"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("final_cost"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("product_id"), DataTypes.UUID)
                .build());
        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("rents_by_product"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("product_id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("purchase_date"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("id"),DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("delivery_data"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("final_cost"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .build());

    }
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Override
    public List<Purchase> findByClientId(UUID id) {
        Select select = QueryBuilder.selectFrom("rents_by_client").all()
                .where(Relation.column("client_id").isEqualTo(literal(id)));
        ResultSet resultSet = session.execute(select.build());
        List<Row> result = resultSet.all();
        List<Purchase>purchases = new ArrayList<>();
        for(int i = 0; i < result.size(); i++){
            Purchase purchase = mapRowToPurchase(result.get(i));
            purchases.add(purchase);
        }
        return purchases;
    }


    @Override
    @StatementAttributes(consistencyLevel = "QUORUM")
    public List<Purchase> findByProductId(UUID id) {
        Select select = QueryBuilder.selectFrom("rents_by_product").all()
                .where(Relation.column("product_id").isEqualTo(literal(id)));
        ResultSet resultSet = session.execute(select.build());
        List<Row> result = resultSet.all();
        List<Purchase>purchases = new ArrayList<>();
        for(int i = 0; i < result.size(); i++){
            Purchase purchase = mapRowToPurchase(result.get(i));
            purchases.add(purchase);
        }
        return purchases;
    }


    @Override
    @StatementAttributes(consistencyLevel = "QUORUM")
    public Purchase  savePurchase(Purchase purchase) {
        Product product = productRepository.findProductById(purchase.getProducts());
        product.setNumberOfProducts(product.getNumberOfProducts()-1);
        productRepository.update(product);
        Instant purchaseDate = Instant.now();

        LocalDate date = purchase.getDeliveryDate();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        Instant deliveryDate = zonedDateTime.toInstant();


        String insertQuery = "INSERT INTO shop.rents_by_client (client_id, purchase_date,id,delivery_data, final_cost,product_id) VALUES (?, ?, ?, ?,?,?)";
        PreparedStatement preparedStatement = session.prepare(insertQuery);
        //session.execute(preparedStatement.bind(purchase.getClient(),purchaseDate,purchase.getId(),deliveryDate ,purchase.getFinalCost(),purchase.getProducts()));

        String insertQuery2 = "INSERT INTO shop.rents_by_product (product_id,id, purchase_date,delivery_data, final_cost,client_id) VALUES (?, ?, ?, ?,?,?)";
        PreparedStatement preparedStatement2 = session.prepare(insertQuery2);
        //session.execute(preparedStatement2.bind(purchase.getProducts(),purchase.getId(),purchaseDate,deliveryDate ,purchase.getFinalCost(),purchase.getClient()));
        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(preparedStatement.bind(purchase.getClient(),purchaseDate,purchase.getId(),deliveryDate ,purchase.getFinalCost(),purchase.getProducts()))
                .addStatement(preparedStatement2.bind(purchase.getProducts(),purchase.getId(),purchaseDate,deliveryDate ,purchase.getFinalCost(),purchase.getClient()))
                .build();
        session.execute(batchStatement);
        return purchase;
    }

    @Override
    @StatementAttributes(consistencyLevel = "QUORUM")
    public void endPurchase(Purchase rent ){
        Instant date = Instant.now();
        Update endPurchaseInClient = QueryBuilder.update("rents_by_client")
                .setColumn("delivery_data",QueryBuilder.literal(date))
                .where(Relation.column("client_id").isEqualTo(literal(rent.getClient())))
                .where(Relation.column("purchase_date").isEqualTo(literal(rent.getPurchaseDate())));

        Update endPurchaseInProduct = QueryBuilder.update("rents_by_product")
                .setColumn("delivery_data",QueryBuilder.literal(date))
                .where(Relation.column("product_id").isEqualTo(literal(rent.getProducts())))
                .where(Relation.column("purchase_date").isEqualTo(literal(rent.getPurchaseDate())));
        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(endPurchaseInClient.build())
                .addStatement(endPurchaseInProduct.build())
                .build();
        session.execute(batchStatement);

    }
    private Purchase mapRowToPurchase(Row row) {
        Instant purchaseDateInstant = row.getInstant("purchase_date");
        Instant deliveryDateInstant = row.getInstant("delivery_data");
        Purchase purchase = new Purchase();
        purchase.setId(row.getUuid("id"));
        assert purchaseDateInstant != null;
        purchase.setPurchaseDate(purchaseDateInstant.atZone(ZoneId.systemDefault()).toLocalDate());
        assert deliveryDateInstant != null;
        purchase.setDeliveryDate(deliveryDateInstant.atZone(ZoneId.systemDefault()).toLocalDate());
        purchase.setFinalCost(row.getDouble("final_cost"));
        purchase.setClient(row.getUuid("client_id"));
        purchase.setProducts(row.getUuid("product_id"));
        return purchase;
    }


}
