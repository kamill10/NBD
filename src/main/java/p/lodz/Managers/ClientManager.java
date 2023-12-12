package p.lodz.Managers;

import com.datastax.oss.driver.api.core.CqlSession;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.CassandraImplementations.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientManager {
    private final p.lodz.Repositiories.ClientRepository clientRepository;

    public ClientManager(CqlSession session) {
        this.clientRepository = new ClientRepository(session);
    }

    public Client getClient(UUID id){
        return clientRepository.findClientById(id);
    }

    public void  registerClient(Client client ) {

        clientRepository.saveClient(client);
    }

}
