package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Client;

import java.util.List;

public interface ClientRepository {
    Client saveClient(Client client);
    boolean deleteClient(ObjectId id);
    Client findClientById(ObjectId id);
    List<Client> findAllClients();
}
