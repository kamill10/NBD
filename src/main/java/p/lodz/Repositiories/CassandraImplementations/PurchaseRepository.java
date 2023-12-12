package p.lodz.Repositiories.CassandraImplementations;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseRepository implements p.lodz.Repositiories.PurchaseRepository {

    CqlSession session;

    public PurchaseRepository(CqlSession session) {
        this.session = session;
    }

    void createTable(){
        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("rents"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("purchase_date"),DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("delivery_data"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("final_cost"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("product_id"), DataTypes.UUID)
                .build());

    }
    @Override
    public Client findByClientId(UUID id) {
       return null;
    }

    @Override
    public Product findByProductId(UUID id) {
        return null;
    }


    @Override
    public void  savePurchase(Purchase purchase) {
        String insertQuery = "INSERT INTO shop.rents (id, purchase_date,delivery_data, final_cost,client_id,product_id) VALUES (?, ?, ?, ?,?,?)";
        PreparedStatement preparedStatement = session.prepare(insertQuery);
        session.execute(preparedStatement.bind(purchase.getId(),purchase.getPurchaseDate(),purchase.getDeliveryDate(),purchase.getFinalCost(),purchase.getClient(),purchase.getProducts()));

    }

    @Override
    public void endPurchase(UUID id){

    }


}
