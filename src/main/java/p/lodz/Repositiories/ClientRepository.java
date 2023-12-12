package p.lodz.Repositiories;

import org.bson.types.ObjectId;
import p.lodz.Model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    void  saveClient(Client client);
    boolean deleteClient(Client client );
    Client findClientById(UUID id);
    void update(Client client );
}
