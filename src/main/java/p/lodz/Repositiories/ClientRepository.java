package p.lodz.Repositiories;

import p.lodz.Model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    Client saveClient(Client client);
    boolean deleteClient(UUID id);
    Client findClientById(UUID id);
    List<Client> findAllClients();
}
