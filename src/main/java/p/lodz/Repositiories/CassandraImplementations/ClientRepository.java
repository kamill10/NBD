package p.lodz.Repositiories.CassandraImplementations;

import com.datastax.oss.driver.api.core.CqlSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements p.lodz.Repositiories.ClientRepository {

    private final CqlSession session;

    public ClientRepository(CqlSession session) {
        this.session = session;
    }


    @Override
    public Client saveClient(Client client) {
        return null;
    }

    @Override
    public boolean deleteClient(ObjectId id) {
        return true;
    }

    @Override
    public Client findClientById(ObjectId id) {
        return null;
    }

    @Override
    public List<Client> findAllClients() {
        return null;
    }
}
