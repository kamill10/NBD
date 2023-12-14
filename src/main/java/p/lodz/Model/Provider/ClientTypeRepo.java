package p.lodz.Model.Provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.CassandraImplementations.ClientRepository;

import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientTypeRepo {
    private static CqlSession session;
    ClientTypeProvider clientTypeProvider;

    public ClientTypeRepo(CqlSession session) {
        ClientTypeRepo.session = session;
        clientTypeProvider = new ClientTypeProvider(session);
        createClientTypeTable();
    }

    private static void createClientTypeTable() {
        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("clienttype"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("discriminator"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("shorter_delivery_time"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("client_discount"), DataTypes.DOUBLE)
                .build());

    }
    public void create(ClientType clientType){
        clientTypeProvider.create(clientType);
    }
    public boolean findByType(String type){
        Select select = QueryBuilder.selectFrom("clienttype").all()
                .where(Relation.column("discriminator").isEqualTo(literal(type)));
        ResultSet resultSet = session.execute(select.build());
        List<Row> result = resultSet.all();
        return !result.isEmpty();

    }
}
