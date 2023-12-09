package p.lodz.Repositiories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
public class CassandraConfig {
    private static CqlSession session;
    CassandraConfig(){
        initSession();
    }
    public void initSession(){
        session = CqlSession.builder()
                  .addContactPoint(new InetSocketAddress("cassandra1",9042))
                  .addContactPoint(new InetSocketAddress("cassandara2",9043))
                  .withLocalDatacenter("dc1")
                  .withAuthCredentials("cassandra","cassandrapassword")
                   .build();
        session.execute(SchemaBuilder.createKeyspace(CqlIdentifier.fromCql("shop"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .build());
        session.close();
    }
}
