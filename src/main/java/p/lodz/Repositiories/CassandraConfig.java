package p.lodz.Repositiories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
public class CassandraConfig implements AutoCloseable {

    private static CqlSession session;
    public CassandraConfig(){
        initSession();
    }
    public void initSession(){
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withAuthCredentials("cassandra", "cassandra")
                .withLocalDatacenter("dc1")
                .withKeyspace(CqlIdentifier.fromCql("shop"))
                .build();


    }
    public CqlSession getSession(){
        return session;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
