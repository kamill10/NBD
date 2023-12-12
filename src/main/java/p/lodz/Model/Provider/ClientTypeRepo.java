package p.lodz.Model.Provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.CassandraImplementations.ClientRepository;

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
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("client_discount"),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("shorter_delivery_time"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("discriminator"), DataTypes.TEXT)
                .build());

    }
    public void create(ClientType clientType){
        clientTypeProvider.create(clientType);
    }
}
